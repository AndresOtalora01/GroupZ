package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatsListBinding
import cat.copernic.groupz.model.ChatListRow
import com.google.android.material.bottomnavigation.BottomNavigationView


class ChatsListFragment : Fragment(), ChatListAdapter.OnItemClickListener {
    private var chatListRecycler: RecyclerView? = null
    private var chatListAdapter: ChatListAdapter? = null
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentChatsListBinding
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatsListBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.chats)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE
        //FirebaseClient.getDatabaseChatsFromUser(FirebaseClient.auth.currentUser?.email as String)
        chatListRecycler = view.findViewById(R.id.chatViewList)
        val categoryItemList: MutableList<ChatListRow> = ArrayList()
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Manolo", "Tu: Hola Manolo"))
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Andrea", "Tu: Hola Manolo"))
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Carla", "Tu: Hola Manolo"))
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Emilio", "Tu: Hola Manolo"))
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Marta", "Tu: Hola Manolo"))
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Pedro", "Tu: Hola Manolo"))

        setSearchView()

        setChatListRecycler(categoryItemList)
    }

    private fun setSearchView() {
        searchView = binding.etSearchChat
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                chatListAdapter!!.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                chatListAdapter!!.filter.filter(newText)
                return true
            }

        })
    }


    private fun setChatListRecycler(chatList: List<ChatListRow>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        chatListRecycler!!.layoutManager = layoutManager
        chatListAdapter = ChatListAdapter()
        chatListRecycler!!.adapter = chatListAdapter
        chatListAdapter!!.setData(chatList, this)
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_chatsListFragment_to_chatFragment)
    }

    private fun addChats() {


    }


    //Falta programar internamente la recogida de datos de los chats.

}