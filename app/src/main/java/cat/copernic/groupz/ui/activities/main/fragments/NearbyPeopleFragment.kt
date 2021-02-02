package cat.copernic.groupz.ui.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentNearbyPeopleBinding


class NearbyPeopleFragment : Fragment() {

    private lateinit var binding: FragmentNearbyPeopleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_people, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNearbyPeopleBinding.bind(view)
        binding.btToProfile.setOnClickListener {

            findNavController().navigate(R.id.action_nearbyPeopleFragment_to_profileFragment)
        }

    }
}
