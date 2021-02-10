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
        userToFragment(FirebaseClient.auth.currentUser?.email as String)
        binding.btToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    fun userToFragment(userMail: String) {
        val data = FirebaseClient.db.collection("Users").document(userMail)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.tvNameProfile.text = it.get("Name") as String
                    binding.tvHobbiesProfile.text = it.get("Hobbies") as String
                    binding.tvDescriptionProfile.text = it.get("Description") as String
                    if (FirebaseClient.auth.currentUser?.email == it.get("Mail") as String){
                        binding.btToEditProfile.visibility = View.VISIBLE
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d(FirebaseClient.TAG, "Failure")
            }
    }

}