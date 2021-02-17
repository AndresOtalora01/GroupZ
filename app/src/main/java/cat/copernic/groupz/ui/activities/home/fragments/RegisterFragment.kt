package cat.copernic.groupz.ui.activities.home.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentRegisterBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding //Instanciamos binding para recoger los elementos del fragmento
    var db = FirebaseFirestore.getInstance() //Instanciamos la base de datos
    var TAG = "registerFragment" //Tag para el log
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =FragmentRegisterBinding.bind(view) //Cuando la vista se crea, se asigna la vista al binding, y asi puede acceder a los elementos.
        builder = AlertDialog.Builder(context) //Preparamos el Alert dialog
        builder.setTitle("Registre")
        builder.setPositiveButton("Aceptar", null)

        binding.tvAccountLog.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
        binding.btnRegister.setOnClickListener { //cuando se presiona el boton de register.

            if (comprovate()) {
                FirebaseClient.auth
                    .createUserWithEmailAndPassword( //Se hace el usuario con el mail y la contraseña
                        binding.etMail.text.toString(),
                        binding.etPass.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) { //Si es correcto
                            Log.d(TAG, "Auth Worked Succesful")
                            var userAdd:User = createUserObj()
                            if (FirebaseClient.addDatabaseUser(userAdd)){
                                Log.d(TAG, "Firestore Added Succesfully")
                                builder.setMessage(R.string.userCreated);
                                val dialog = builder.create()
                                dialog.show()
                                startActivity( Intent(context, MainActivity::class.java))

                            } else {
                                Log.w(TAG, "Error adding document to Firestore")
                                builder.setMessage(R.string.errorUserNotCreated);
                                //FirebaseAuth.getInstance().currentUser?.delete()
                                val dialog = builder.create()
                                dialog.show()

                            }

                        } else {
                            Log.w(TAG, "Error adding user to Authentication")
                            builder.setMessage(R.string.errorUserNotCreated);
                            val dialog = builder.create()
                            dialog.show()

                        }

                    }
            }

        }

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
        var birthInput = binding.etBirth.text.toString()
        val date_pattern: Pattern =
            Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$");
        if (binding.etBirth.text.isEmpty()) {
            binding.etBirth.error = getString(R.string.errorEmptyField)
            return false
        } else if (!date_pattern.matcher(birthInput).matches()) {
            binding.etMail.error = getString(R.string.errorBadDate)
            return false
        } else {
            binding.etMail.error = null
            return true

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
    private fun isMatchPassword(): Boolean{
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



    private fun comprovate(): Boolean{
        var boolean: Boolean = true
        if (!isValidName()){
            boolean = false
        }
        if (!isValidBirth()){
            boolean = false
        }

        if (!isValidHobbies()){
            boolean = false
        }
        if (!isValidLocation()){
            boolean = false
        }

        if (!isValidEmail()){
            boolean = false
        }

        if (!isValidPassword()){
            boolean = false
        }
        if (!isMatchPassword()){
            boolean = false
        }

        if (!isValidTerms()){
            boolean = false
        }

        return boolean

    }

    fun createUserObj() : User {
        var userObj = User(
            binding.etName.text.toString(),
            binding.etMail.text.toString(),
            binding.etBirth.text.toString(),
            binding.etHobbies.text.toString(),
            "",
            binding.etLocation.text.toString()
        )
        return userObj

    }



}