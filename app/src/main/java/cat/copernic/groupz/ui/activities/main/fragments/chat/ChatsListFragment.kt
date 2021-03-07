package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatsListBinding
import cat.copernic.groupz.model.ChatListRow
import cat.copernic.groupz.network.FirestoreUtil
import cat.copernic.groupz.ui.activities.main.fragments.profile.NearbyPeopleFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_chats_list.*
import kotlinx.android.synthetic.main.fragment_nearby_people.*
import kotlinx.android.synthetic.main.fragment_nearby_people.nearbyPeopleList


class ChatsListFragment : Fragment() {
    private var chatListRecycler: RecyclerView? = null
    private var chatListAdapter: ChatListAdapter? = null
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentChatsListBinding
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var  categoryItemList: MutableList<ChatListRow>
    private lateinit var chatsListener : ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var chatsSection: Section
    private lateinit var chats: List<Item>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_chats_list, container, false)
        binding = FragmentChatsListBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.chats)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE
        chatsListener = FirestoreUtil.listOfChatsListener(this::updateRecyclerView, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // setSearchView()
    }

//    private fun setSearchView() {
//        searchView = binding.etSearchChat
//        searchView.setOnQueryTextListener(object :
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                chatListAdapter!!.filter.filter(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                chatListAdapter!!.filter.filter(newText)
//                return true
//            }
//
//        })
//    }


    private val onItemClick = OnItemClickListener { item, view ->
        if (item is ChatListItem) {
            val action = ChatsListFragmentDirections.actionChatsListFragmentToChatFragment(item.user.name, item.user.mail)
            findNavController().navigate(action)
        }
    }



    private fun updateRecyclerView(items: List<Item>) {
        chats = items
        fun init() {
            chatViewList.apply {
                layoutManager = LinearLayoutManager(this@ChatsListFragment.context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    chatsSection = Section(items)
                    add(chatsSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = chatsSection.update(items)

        if(shouldInitRecyclerView)
            init()
        else
            updateItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        FirestoreUtil.removeListener(chatsListener)
        shouldInitRecyclerView = true
    }

}