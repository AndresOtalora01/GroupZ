package cat.copernic.groupz.ui.activities.home.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentRegisterBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.StringBuilder
import java.util.*
import java.util.jar.Manifest
import java.util.regex.Pattern


class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentRegisterBinding //Instanciamos binding para recoger los elementos del fragmento
    var db = FirebaseFirestore.getInstance() //Instanciamos la base de datos
    var TAG = "registerFragment" //Tag para el log
    private lateinit var builder: AlertDialog.Builder

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    private lateinit var client: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        binding =
            FragmentRegisterBinding.bind(view) //Cuando la vista se crea, se asigna la vista al binding, y asi puede acceder a los elementos.
        builder = AlertDialog.Builder(context) //Preparamos el Alert dialog
        builder.setTitle("Registre")
        builder.setPositiveButton("Aceptar", null)
        picKDate()
        binding.tvAccountLog.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        binding.btnRegister.setOnClickListener { //cuando se presiona el boton de register.

            if (comprovate()) {
                binding.btnRegister.visibility = View.GONE
                binding.shimmerViewContainer.visibility = View.VISIBLE
                binding.shimmerViewContainer.stopShimmer()
                FirebaseClient.auth
                    .createUserWithEmailAndPassword( //Se hace el usuario con el mail y la contraseña
                        binding.etMail.text.toString(),
                        binding.etPass.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) { //Si es correcto
                            Log.d(TAG, "Auth Worked Succesful")
                            var userAdd: User = createUserObj()
                            if (FirebaseClient.addDatabaseUser(userAdd)) {
                                Log.d(TAG, "Firestore Added Succesfully")
                                builder.setMessage(R.string.userCreated);
                                val dialog = builder.create()
                                dialog.show()
                                startActivity(Intent(context, MainActivity::class.java))
                            }
                        } else {
                            binding.shimmerViewContainer.visibility = View.GONE
                            binding.shimmerViewContainer.stopShimmer()
                            binding.btnRegister.visibility = View.VISIBLE
                            Log.w(TAG, "Error adding user to Authentication")
                            builder.setMessage(R.string.errorUserNotCreated);
                            val dialog = builder.create()
                            dialog.show()
                        }

                    }
            }

        }

        client = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.etLocation.setOnClickListener {
            checkPermissions()
        }
        return binding.root
    }



    fun isValidName(): Boolean {
        var nameInput = binding.etName.text.toString()
        if (nameInput.isEmpty()) {
            binding.etName.error = getString(R.string.errorEmptyField)
            return false

        } else {
            binding.etName.error = null
            return true
        }

    }

    private fun isValidBirth(): Boolean {
        var rdate = Calendar.getInstance().get(Calendar.DATE) - savedDay
        var rmonth = (Calendar.getInstance().get(Calendar.MONTH) + 1) - savedMonth
        var ryear = Calendar.getInstance().get(Calendar.YEAR) - savedYear
        if (binding.etBirth.text.isEmpty()) {
            binding.etBirth.error = getString(R.string.errorEmptyField)
            return false
        } else {
            if (rmonth >= 0 && rdate >= 0 && ryear >= 16) {
                binding.etBirth.error = null
                return true
            } else if ((ryear - 1) >= 16) {
                binding.etBirth.error = null
                return true
            } else {
                binding.etBirth.error = "La edad mínima para crear una cuenta son 16 años."
                return false
            }
        }

    }

    private fun isValidHobbies(): Boolean {
        if (binding.etHobbies.text.isEmpty()) {
            binding.etHobbies.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etHobbies.error = null
            return true
        }
    }

    private fun isValidLocation(): Boolean {
        if (binding.etLocation.text.isEmpty()) {
            binding.etLocation.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etLocation.error = null
            return true
        }
    }

    private fun isValidEmail(): Boolean {
        val mailRegCheck =
            android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etMail.text.toString())
                .matches()   // matched: true
        Log.d(TAG, "MailRegMatch: " + mailRegCheck)
        if (binding.etMail.text.isEmpty()) {
            binding.etMail.error = getString(R.string.errorEmptyField)
            return false
        } else if (!mailRegCheck) {
            binding.etMail.error = getString(R.string.errorNotValidMail)
            return false
        } else {
            binding.etMail.error = null
            return true

        }
    }

    private fun isValidPassword(): Boolean {
        var passwordInput = binding.etPass.text.toString()
        val password_pattern: Pattern =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")

        if (passwordInput.isEmpty()) {
            binding.etPass.error = getString(R.string.errorEmptyField)
            return false
        } else if (!password_pattern.matcher(passwordInput).matches()) {
            binding.etPass.error = getString(R.string.errorWeakPassword)
            return false
        } else {
            binding.etPass.error = null
            return true

        }

    }

    private fun isMatchPassword(): Boolean {
        if (binding.etPassConfirm.text.isEmpty()) {
            binding.etPassConfirm.error = getString(R.string.errorEmptyField)
            return false
        } else if (binding.etPass.text.toString() != binding.etPassConfirm.text.toString()) {
            binding.etPassConfirm.error = getString(R.string.errorPasswordDontMatch)
            return false
        } else {
            binding.etPassConfirm.error = null
            return true
        }

    }

    private fun isValidTerms(): Boolean {
        if (!binding.cbTerms.isChecked) {
            binding.cbTerms.error = getString(R.string.errorNotCheck)
            return false
        } else {
            binding.etMail.error = null
            return true
        }
    }


    private fun comprovate(): Boolean {
        var boolean: Boolean = true
        if (!isValidName()) {
            boolean = false
        }
        if (!isValidBirth()) {
            boolean = false
        }

        if (!isValidHobbies()) {
            boolean = false
        }
        if (!isValidLocation()) {
            boolean = false
        }

        if (!isValidEmail()) {
            boolean = false
        }

        if (!isValidPassword()) {
            boolean = false
        }
        if (!isMatchPassword()) {
            boolean = false
        }

        if (!isValidTerms()) {
            boolean = false
        }

        return boolean

    }

    fun createUserObj(): User {
        var userObj = User(
            binding.etName.text.toString(),
            binding.etMail.text.toString(),
            binding.etBirth.text.toString(),
            binding.etHobbies.text.toString(),
            "https://firebasestorage.googleapis.com/v0/b/groupz-c793a.appspot.com/o/imageProfile%2FDefault_profile.png?alt=media&token=108bdb29-c173-48ad-807d-31c3d2a5ce0e",
            "",
            binding.etLocation.text.toString()
        )
        return userObj

    }

    private fun getDateCalendar() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun picKDate() {
        getDateCalendar()
        binding.etBirth.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        binding.etBirth.setText("$savedDay/$savedMonth/$savedYear")
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            val cancellationTokenSource = CancellationTokenSource()
            client.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token).addOnCompleteListener {
                if(it.result != null) {
                    latitude = it.result.latitude
                    longitude = it.result.longitude

                    val geoCoder = Geocoder(context)
                    val addresses : List<Address> = geoCoder.getFromLocation(latitude, longitude, 1)
                    val address : Address = addresses[0]
                    val locality : String = address.locality
                    //val subLocality : String = address.subLocality
                    binding.etLocation.setText(locality)
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100 && (grantResults.isNotEmpty()) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation()
        }
    }
}