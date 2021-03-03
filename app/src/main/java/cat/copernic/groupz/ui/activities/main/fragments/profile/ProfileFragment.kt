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
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentProfileBinding
import cat.copernic.groupz.model.User
import cat.copernic.groupz.network.FirebaseClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.my_profile)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnMenu)?.visibility = View.GONE
        activity?.findViewById<DrawerLayout>(R.id.drawerLayout)?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.GONE
        binding = FragmentProfileBinding.bind(view)
        userToFragment(FirebaseClient.auth.currentUser?.email as String)
        binding.btToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    fun userToFragment(userMail: String) {
        binding.btToEditProfile.visibility = View.VISIBLE
        val data = FirebaseClient.db.collection("Users").document(userMail)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    binding.tvNameProfile.text = it.get("Name") as String
                    binding.tvHobbiesProfile.text = it.get("Hobbies") as String
                    binding.tvDescriptionProfile.text = it.get("Description") as String
                    Glide.with(this)
                        .load(it.get("Image").toString())
                        .placeholder(R.drawable.animated_progress)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(binding.ivImage)
                }

            }
    }

}