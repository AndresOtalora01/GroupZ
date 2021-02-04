package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentEditProfileBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
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


        binding.btnSaveProfile.setOnClickListener {
            if (saveData()) {
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
            }

        }
    }


    fun saveData() : Boolean {
        val saved = true

        return saved
    }
}

