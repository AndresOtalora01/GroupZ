package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatsListBinding
import cat.copernic.groupz.model.ChatListRow


class ChatsListFragment : Fragment(), ChatListAdapter.OnItemClickListener, SearchView.OnQueryTextListener{
    private var chatListRecycler: RecyclerView? = null
    private var chatListAdapter: ChatListAdapter? = null

    private lateinit var binding: FragmentChatsListBinding
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
        //FirebaseClient.getDatabaseChatsFromUser(FirebaseClient.auth.currentUser?.email as String)
        chatListRecycler = view.findViewById(R.id.chatViewList)
        val categoryItemList : MutableList<ChatListRow> = ArrayList()
        categoryItemList.add(ChatListRow(R.drawable.pedra, "Manolo", "Tu: Hola Manolo"))
        setChatListRecycler(categoryItemList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }
    private fun setChatListRecycler(chatList: List<ChatListRow>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        chatListRecycler!!.layoutManager = layoutManager
        chatListAdapter = ChatListAdapter(chatList, this)
        chatListRecycler!!.adapter = chatListAdapter
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_chatsListFragment_to_chatFragment)
    }

    private fun addChats(){


    }

    //Falta programar internamente la recogida de datos de los chats.

}