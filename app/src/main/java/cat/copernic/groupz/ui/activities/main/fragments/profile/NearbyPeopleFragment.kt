 package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentNearbyPeopleBinding
import cat.copernic.groupz.network.FirestoreUtil
import cat.copernic.groupz.ui.activities.main.fragments.chat.AppConstants
import cat.copernic.groupz.ui.activities.main.fragments.chat.ChatsListFragmentDirections
import cat.copernic.groupz.ui.activities.main.fragments.chat.UserItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_nearby_people.*


class NearbyPeopleFragment : Fragment(){
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentNearbyPeopleBinding
    private lateinit var userListenerRegistration : ListenerRegistration
    private lateinit var peopleSection: Section
    private lateinit var people: List<Item>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_nearby_people, container, false)

        binding = FragmentNearbyPeopleBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.near_people)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE

        userListenerRegistration = FirestoreUtil.addUsersListener(this::updateRecyclerView)

        return binding.root
    }

    private fun updateRecyclerView(items: List<Item>) {
        people = items
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            spanCount = 2
        }
        fun init() {
            nearbyPeopleList.apply {
                layoutManager = GridLayoutManager(this@NearbyPeopleFragment.context, groupAdapter.spanCount).apply {
                    spanSizeLookup = groupAdapter.spanSizeLookup
                }
                adapter = groupAdapter.apply {
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
        }
            init()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirestoreUtil.removeListener(userListenerRegistration)
    }


    private val onItemClick = OnItemClickListener { item, view ->
        if (item is UserItem) {
         //  val action = NearbyPeopleFragmentDirections.actionNearbyPeopleFragmentToChatFragment(item.user.name , item.userId)
            val action = NearbyPeopleFragmentDirections.actionNearbyPeopleFragmentToProfileFragment(item.user, false)
           findNavController().navigate(action)
        }
    }

}
