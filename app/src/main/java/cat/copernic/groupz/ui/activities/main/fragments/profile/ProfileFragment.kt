package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentProfileBinding
import cat.copernic.groupz.model.User

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
        binding.btToEditProfile.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        

    }
    fun userToFragment(userObj : User){
        binding.tvNameProfile.setText(userObj.name)
        binding.tvDescriptionProfile.setText(userObj.description)
        binding.tvHobbiesProfile.setText(userObj.description)
    }

}