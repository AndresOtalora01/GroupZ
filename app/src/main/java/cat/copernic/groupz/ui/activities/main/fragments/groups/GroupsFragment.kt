package cat.copernic.groupz.ui.activities.main.fragments.groups

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
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentDeleteConfirmationBinding
import cat.copernic.groupz.databinding.FragmentGroupsBinding
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.model.Group
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.fragments.events.CategoryItem
import com.google.android.material.bottomnavigation.BottomNavigationView


class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var titles : ArrayList<String>
    private lateinit var images : ArrayList<String>
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.my_groups)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE

        binding.fabCreatGroup.setOnClickListener{
        findNavController().navigate(R.id.action_groupsFragment_to_create_GroupFragment)
    }
        binding.shimmerViewContainer.startShimmer()
        getDatabaseMygroups()
    }

    fun setRecyclerView(titles: ArrayList<String>,images: ArrayList<String>){
        recyclerView = binding.recyclerViewGroups

        recyclerView.adapter = GroupsAdapter(titles, images, requireContext())
        val manager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager

    }

    fun getDatabaseMygroups(){
        var groupresult = arrayListOf<Group>()
        var data = FirebaseClient.db.collection("Group")
        images = arrayListOf()
        titles = arrayListOf()
        data.get().addOnCompleteListener { groups ->
            for (group in groups.result!!) {
                Log.d("dsusdfuisdbfksdifb", "${group.id} => ${group.data}")
                var members = group.data["Members"] as List<String>
                if (members.contains(FirebaseClient.auth.currentUser?.email.toString())) {
                    groupresult.add(
                        Group(
                            group.data["Admin"].toString(),
                            group.data["Description"].toString(),
                            group.data["Image"].toString(),
                            members,
                            group.data["Hobbies"].toString(),
                            group.data["Name"].toString()
                        )
                    )
                }
            }
            for (group in groupresult){
                images.add(group.image)
                titles.add(group.name)
            }
            setRecyclerView(titles,images);
            binding.shimmerViewContainer.visibility = View.GONE
            binding.recyclerViewGroups.visibility = View.VISIBLE


        }

    }
}