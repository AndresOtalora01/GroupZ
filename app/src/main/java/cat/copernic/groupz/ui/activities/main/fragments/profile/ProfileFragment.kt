package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentProfileBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        //var userA = createUserObject()
        //userToFragment(userA)
        binding.btToEditProfile.visibility = View.VISIBLE

        binding.btToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    fun createUserObject(): User {
        var userObject = User()
        userObject = FirebaseClient.getDatabaseUser(FirebaseClient.auth.currentUser?.email.toString())
        return userObject
    }

    fun userToFragment(userObj: User) {
        if (FirebaseClient.auth.currentUser?.email == userObj.mail) {
            binding.btToEditProfile.visibility = View.VISIBLE
        }
        binding.tvNameProfile.text = userObj.name
        binding.tvDescriptionProfile.text = userObj.description
        binding.tvHobbiesProfile.text = userObj.hobbies
    }

}