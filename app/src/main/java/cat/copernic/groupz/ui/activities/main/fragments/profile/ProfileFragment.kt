package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentProfileBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatFragmentArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.bind(view)
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text =
            getString(R.string.my_profile)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnMenu)?.visibility = View.GONE
        activity?.findViewById<DrawerLayout>(R.id.drawerLayout)
            ?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE

        if (!args.isMyProfile) {
            val user = args.user
            binding.tvNameProfile.text = user!!.name
            val hobbies = user.hobbies.split(",").map {
                it.trim()
            }
            binding.tag1.text = hobbies[0]
            binding.tag1.visibility = View.VISIBLE
            if (hobbies.size > 1) {
                binding.tag2.text = hobbies[1]
                binding.tag2.visibility = View.VISIBLE
            }

            if (hobbies.size > 2) {
                binding.tag3.text = hobbies[2]
                binding.tag3.visibility = View.VISIBLE
            }

            binding.tvDescriptionProfile.text = user.description
            Glide.with(this)
                .load(user.image)
                .placeholder(R.drawable.animated_progress)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.ivImage)

            binding.btnMessageProfile.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToChatFragment(
                    user.name,
                    user.mail
                )
                findNavController().navigate(action)
            }
        } else {
            userToFragment(FirebaseClient.auth.currentUser?.email as String)
            binding.btToEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
        }
        return binding.root
    }

    fun userToFragment(userMail: String) {
        binding.btToEditProfile.visibility = View.VISIBLE
        binding.linearLayoutButtons.visibility = View.GONE
        val data = FirebaseClient.db.collection("Users").document(userMail)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.tvNameProfile.text = it.get("Name") as String
                    binding.tag1.text = it.get("Hobbies") as String
                    binding.tvDescriptionProfile.text = it.get("Description") as String
                    var string = it.get("Hobbies") as String
                    var hobbies = string.split(",").map {hobbie ->
                        hobbie.trim()
                    }
                    binding.tag1.text = hobbies[0]
                    binding.tag1.visibility = View.VISIBLE
                    if (hobbies.size > 1) {
                        binding.tag2.text = hobbies[1]
                        binding.tag2.visibility = View.VISIBLE
                    }

                    if (hobbies.size > 2) {
                        binding.tag3.text = hobbies[2]
                        binding.tag3.visibility = View.VISIBLE
                    }
                    Glide.with(this)
                        .load(it.get("Image").toString())
                        .placeholder(R.drawable.animated_progress)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.ivImage)
                }

            }
    }


}