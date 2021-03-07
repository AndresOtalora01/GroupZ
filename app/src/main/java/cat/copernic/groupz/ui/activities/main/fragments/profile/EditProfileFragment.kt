package cat.copernic.groupz.ui.activities.main.fragments.profile


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlin.math.log


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    var userAuth = FirebaseAuth.getInstance().currentUser
    private var GALERI_INTENT = 1000
    private var uri: Uri? = Uri.EMPTY
    private lateinit var userData: User

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
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE

        getData(FirebaseClient.auth.currentUser?.email as String)

        binding.ivAddEdit.setOnClickListener {
            pickImageFromGallery()

        }
        binding.btnSave.setOnClickListener {
            binding.btnSave.visibility = View.GONE
            binding.btnSaveGone.visibility = View.VISIBLE
           // binding.shimmerViewContainer.startShimmer()
            upload()
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
                        Glide.with(this)
                            .load(it.get("Image").toString())
                            .placeholder(R.drawable.animated_progress)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(binding.ivAddEdit)
                    }
                    binding.etDescriptionEdit.setText(it.get("Description") as String)
                    binding.etLocationEdit.setText(it.get("Location") as String)
                } else {
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
                                Toast.makeText(
                                    context,
                                    "tu perfil a sido editado correctamente",
                                    Toast.LENGTH_LONG
                                ).show()
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
                        Toast.makeText(
                            context,
                            "tu perfil a sido editado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
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


        }
    }

}

