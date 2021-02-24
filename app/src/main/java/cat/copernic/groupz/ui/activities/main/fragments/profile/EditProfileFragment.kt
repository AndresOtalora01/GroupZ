package cat.copernic.groupz.ui.activities.main.fragments.profile


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentEditProfileBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    var userAuth = FirebaseAuth.getInstance().currentUser

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
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.GONE

        getData(FirebaseClient.auth.currentUser?.email as String)

        binding.ivAddEdit.setOnClickListener{
            pickImageFromGallery()

        }
        binding.btnSaveProfile.setOnClickListener {
            if (saveData()) {
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
            }

        }
    }

    fun saveData() : Boolean{
        var userData = User(
            binding.etNameEdit.text.toString(),
            FirebaseClient.auth.currentUser?.email.toString(),
            binding.etDateEdit.text.toString(),
            binding.etHobbieEdit.text.toString(),
            binding.etDescriptionEdit.text.toString(),
            binding.etLocationEdit.text.toString()
        )

        if (FirebaseClient.addDatabaseUser(userData)){
            return true
        } else {
            return false
        }
    }
    fun getData(userMail : String){
        val USERS = "Users"
        val data = FirebaseClient.db.collection(USERS).document(userMail)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.etNameEdit.setText(it.get("Name") as String)
                    binding.etDateEdit.setText(it.get("Birth") as String)
                    binding.etHobbieEdit.setText(it.get("Hobbies") as String)
                    binding.etDescriptionEdit.setText(it.get("Description") as String)
                    binding.etLocationEdit.setText(it.get("Location") as String)
                } else {
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()

                }
                else{
                    //permission from popup denied
                }
            }
        }
    }

}

