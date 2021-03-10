package cat.copernic.groupz.ui.activities.main.fragments.profile


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentEditProfileBinding
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.math.log


class EditProfileFragment : Fragment() , DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentEditProfileBinding
    var userAuth = FirebaseAuth.getInstance().currentUser
    private var GALERI_INTENT = 1000
    private var uri: Uri? = Uri.EMPTY
    private lateinit var userData: User

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear= 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        picKDate()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE

        getData(FirebaseClient.auth.currentUser?.email as String)

        binding.ivAddEdit.setOnClickListener {
            pickImageFromGallery()

        }
        binding.btnSave.setOnClickListener {
            if (comprovate()){
                binding.btnSave.visibility = View.GONE
                binding.btnSaveGone.visibility = View.VISIBLE
                binding.shimmerViewContainer.startShimmer()
                upload()
            }

        }
    }

    fun getData(userMail: String) {
        val USERS = "Users"
        val data = FirebaseClient.db.collection(USERS).document(userMail)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.etNameEdit.setText(it.get("Name") as String)
                    binding.etDateEdit.setText(it.get("Birth") as String)
                    binding.etHobbieEdit.setText(it.get("Hobbies") as String)
                    if (it.get("Image")
                            .toString() != "https://firebasestorage.googleapis.com/v0/b/groupz-c793a.appspot.com/o/imageProfile%2FDefault_profile.png?alt=media&token=108bdb29-c173-48ad-807d-31c3d2a5ce0e"
                    ) {
                        binding.ivIconEditProfile.visibility = View.GONE
                        Glide.with(this)
                            .load(it.get("Image").toString())
                            .placeholder(R.drawable.animated_progress)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding.ivAddEdit)

                    }
                    binding.etDescriptionEdit.setText(it.get("Description") as String)
                    binding.etLocationEdit.setText(it.get("Location") as String)
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun upload() {
        var filePath: StorageReference =
            FirebaseStorage.getInstance().getReference().child("imageProfile")
                .child(uri!!.lastPathSegment.toString())
        if (uri != Uri.EMPTY){
            filePath.putFile(uri!!).addOnSuccessListener {

                var storagepath = it.storage.path;
                var ref = FirebaseStorage.getInstance().getReference(storagepath)
                ref.downloadUrl.addOnSuccessListener {
                    var image: ImageView = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
                        ?.findViewById(R.id.ivProfile)!!
                    Glide.with(requireActivity())
                        .load(it.toString())
                        .placeholder(R.drawable.animated_progress)
                        .into(image)

                    userData = User(
                        binding.etNameEdit.text.toString(),
                        FirebaseClient.auth.currentUser?.email.toString(),
                        binding.etDateEdit.text.toString(),
                        binding.etHobbieEdit.text.toString(),
                        it.toString(),
                        binding.etDescriptionEdit.text.toString(),
                        binding.etLocationEdit.text.toString()
                    )
                    val data = FirebaseClient.db.collection("Users")
                        .document(FirebaseClient.auth.currentUser?.email!!)
                    data.get()
                        .addOnSuccessListener {
                            var imgurl = it.get("Image").toString()
                            if (imgurl != "https://firebasestorage.googleapis.com/v0/b/groupz-c793a.appspot.com/o/imageProfile%2FDefault_profile.png?alt=media&token=108bdb29-c173-48ad-807d-31c3d2a5ce0e") {
                                var storage_ref = FirebaseStorage.getInstance()
                                    .getReferenceFromUrl(it.get("Image").toString())
                                storage_ref.delete()
                            }

                            if (FirebaseClient.addDatabaseUser(userData)) {
                                Log.d(FirebaseClient.TAG, "Firestore Added Succesfully")
                            }
                            activity?.onBackPressed()
                        }
                }
            }

        }else{
            val data = FirebaseClient.db.collection("Users")
                .document(FirebaseClient.auth.currentUser?.email!!)
            data.get()
                .addOnSuccessListener {
                    userData = User(
                        binding.etNameEdit.text.toString(),
                        FirebaseClient.auth.currentUser?.email.toString(),
                        binding.etDateEdit.text.toString(),
                        binding.etHobbieEdit.text.toString(),
                        it.get("Image").toString(),
                        binding.etDescriptionEdit.text.toString(),
                        binding.etLocationEdit.text.toString()
                    )
                    if (FirebaseClient.addDatabaseUser(userData)) {
                        Log.d(FirebaseClient.TAG, "Firestore Added Succesfully")
                    }
                    activity?.onBackPressed()
                }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALERI_INTENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALERI_INTENT && resultCode == Activity.RESULT_OK) {
            uri = data?.data
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.animated_progress)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivAddEdit)
                binding.ivIconEditProfile.visibility = View.GONE
        }
    }
    private fun isValidBirth(): Boolean {
        var rdate = Calendar.getInstance().get(Calendar.DATE) - savedDay
        var rmonth = (Calendar.getInstance().get(Calendar.MONTH) + 1) - savedMonth
        var ryear = Calendar.getInstance().get(Calendar.YEAR) - savedYear
        if (binding.etDateEdit.text.isEmpty()) {
            binding.etDateEdit.error = getString(R.string.errorEmptyField)
            return false
        } else {
            if (rmonth >= 0 && rdate >= 0 && ryear >= 16) {
                binding.etDateEdit.error = null
                return true
            } else if ((ryear - 1) >= 16) {
                binding.etDateEdit.error = null
                return true
            } else {
                binding.etDateEdit.error = "La edad mínima para crear una cuenta son 16 años."
                return false
            }
        }

    }

    fun isValidName(): Boolean {
        var nameInput = binding.etNameEdit.text.toString()
        if (nameInput.isEmpty()) {
            binding.etNameEdit.error = getString(R.string.errorEmptyField)
            return false

        } else {
            binding.etNameEdit.error = null
            return true
        }

    }
    private fun isValidHobbies(): Boolean {
        if (binding.etHobbieEdit.text.isEmpty()) {
            binding.etHobbieEdit.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etHobbieEdit.error = null
            return true
        }
    }
    private fun isValidLocation(): Boolean {
        if (binding.etLocationEdit.text.isEmpty()) {
            binding.etLocationEdit.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etLocationEdit.error = null
            return true
        }
    }
    private fun isValidDescription(): Boolean {
        if (binding.etDescriptionEdit.text.isEmpty()) {
            binding.etDescriptionEdit.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etDescriptionEdit.error = null
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
        if (!isValidDescription()) {
            boolean = false
        }
        return boolean

    }

    private fun getDateCalendar(){
        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun picKDate() {
        getDateCalendar()
        binding.etDateEdit.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month+1
        savedYear = year
        binding.etDateEdit.setText("$savedDay/$savedMonth/$savedYear")
    }

}

