package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentNearbyPeopleBinding
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatListAdapter
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatListRow


class NearbyPeopleFragment : Fragment() {

    private var nearbyPeopleRecycler: RecyclerView? = null
    private var nearbyPeopleAdapter: ProfileNearbyAdapter? = null
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

        nearbyPeopleRecycler = view.findViewById(R.id.nearbyPeopleList)
        val categoryItemList : MutableList<ProfileNearbyRow> = ArrayList()
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Paseo", "Montaña del destino"))

        setNearbyListRecycler(categoryItemList)

    }
    private fun setNearbyListRecycler(profileNearbyList: List<ProfileNearbyRow>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        nearbyPeopleRecycler!!.layoutManager = layoutManager
        nearbyPeopleAdapter = ProfileNearbyAdapter(profileNearbyList)
        nearbyPeopleRecycler!!.adapter = nearbyPeopleAdapter
    }
}
