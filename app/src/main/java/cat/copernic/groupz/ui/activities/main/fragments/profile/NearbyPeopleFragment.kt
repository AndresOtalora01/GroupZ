package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentNearbyPeopleBinding
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatListAdapter
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatListRow


class NearbyPeopleFragment : Fragment(), ProfileNearbyAdapter.OnItemClickListener{

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

        nearbyPeopleRecycler = view.findViewById(R.id.nearbyPeopleList)
        val categoryItemList : MutableList<ProfileNearbyRow> = ArrayList()
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Joan Padilla", "29","Manresa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))
        categoryItemList.add(ProfileNearbyRow(R.drawable.pedra, "Manolo Lama", "23","Terrassa"))

        setNearbyListRecycler(categoryItemList)

    }
    private fun setNearbyListRecycler(profileNearbyList: List<ProfileNearbyRow>) {
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        nearbyPeopleRecycler!!.layoutManager = layoutManager
        nearbyPeopleAdapter = ProfileNearbyAdapter(profileNearbyList,this)
        nearbyPeopleRecycler!!.adapter = nearbyPeopleAdapter
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_nearbyPeopleFragment_to_profileFragment)
    }
}
