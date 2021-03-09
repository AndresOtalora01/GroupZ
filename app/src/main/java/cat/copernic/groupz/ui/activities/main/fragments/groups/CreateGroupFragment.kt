 package cat.copernic.groupz.ui.activities.main.fragments.groups

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentCreateGroupBinding
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.model.Group
import cat.copernic.groupz.network.FirebaseClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


 class CreateGroupFragment : Fragment() {
    val PICK_IMAGE_REQUEST = 1
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentCreateGroupBinding
    private var GALERI_INTENT = 1000
    private var uri: Uri? = Uri.EMPTY
     private lateinit var group: Group
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateGroupBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        btndrawerLayout?.visibility = View.GONE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text =
            getString(R.string.createGroup)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE

        binding.ivImageGroup.setOnClickListener {
            localPictureChooser()
        }

        binding.btnSaveGroup.setOnClickListener {
            if (validateFields()) {
                binding.btnSaveGroup.visibility = View.GONE
                binding.btnSaveGone.visibility = View.VISIBLE
                binding.shimmerViewContainer.startShimmer()
                upload()
            }
        }
    }
    private fun upload() {
        var filePath: StorageReference =
            FirebaseStorage.getInstance().getReference().child("Group")
                .child(uri!!.lastPathSegment.toString())
        filePath.putFile(uri!!).addOnSuccessListener {
            var ref = FirebaseStorage.getInstance().getReference(it.storage.path)
            ref.downloadUrl.addOnSuccessListener {

                var members = arrayListOf<String>()
                members.add(FirebaseClient.auth.currentUser?.email.toString())
                group = Group(
                    FirebaseClient.auth.currentUser?.email.toString(),
                    binding.etDescriptionEdit.text.toString(),
                    it.toString(),
                    members,
                    binding.etHobbieEdit.text.toString(),
                    binding.etNameEdit.text.toString()

                )
                if (FirebaseClient.addDatabaseGroup(group)) {
                    Log.d(FirebaseClient.TAG, "Firestore Added Succesfully")
                    Toast.makeText(
                        context,
                        "tu gripo se ha creado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    activity?.onBackPressed()
                }
            }
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

    private fun isValidName(): Boolean {
        if (binding.etNameEdit.text.isEmpty()) {
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

    private fun validateFields(): Boolean {
        var boolean: Boolean = true
        if (!isValidName()) {
            boolean = false
        }
        if (!isValidDescription()) {
            boolean = false
        }
        if (!isValidHobbies()) {
            boolean = false
        }
        if (uri == Uri.EMPTY) {
            boolean = false
            Toast.makeText(context, "debes poner una foto", Toast.LENGTH_LONG).show()
        }
        return boolean
    }

    private fun localPictureChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALERI_INTENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALERI_INTENT && resultCode == RESULT_OK) {
            uri = data?.data
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.animated_progress)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivImageGroup)
            binding.ivIconPlus.visibility = View.GONE
        }
    }
}