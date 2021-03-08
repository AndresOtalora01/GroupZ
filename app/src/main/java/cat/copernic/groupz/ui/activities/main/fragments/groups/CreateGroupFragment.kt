 package cat.copernic.groupz.ui.activities.main.fragments.groups

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentCreateGroupBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView


class CreateGroupFragment : Fragment() {
    val PICK_IMAGE_REQUEST = 1
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentCreateGroupBinding
    private  var mImageUri: Uri = Uri.EMPTY
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
        if (binding.etDescriptionEdit.text.isEmpty()) {
            binding.etDescriptionEdit.error = getString(R.string.errorEmptyField)
            return false
        } else {
            binding.etDescriptionEdit.error = null
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
        return boolean
    }

    private fun localPictureChooser() {
        var intent: Intent = Intent ()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            Glide.with(requireActivity()).load(mImageUri).into(binding.ivImageGroup)
            binding.ivIconPlus.visibility = View.GONE
        }
    }
}