package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatBinding
import cat.copernic.groupz.model.ChatListRow
import cat.copernic.groupz.model.Message
import cat.copernic.groupz.network.FirebaseClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {
    private var chatRecycler: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        btndrawerLayout.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.GONE

        chatRecycler = view.findViewById(R.id.messagesView)
        val categoryItemList : MutableList<Message> = ArrayList()
      //  categoryItemList.add(Message("Hola", "Manolo"))
       // categoryItemList.add(Message("Hola", FirebaseClient.auth.currentUser?.email as String))
      //  categoryItemList.add(Message("Como Estas?", "Manolo"))
        setChatRecycler(categoryItemList)

//        binding.sendButton.setOnClickListener{
//            binding.messageText.setText("")
//        }
    }

    private fun setChatRecycler(Messages: List<Message>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        chatRecycler!!.layoutManager = layoutManager
        chatAdapter = ChatAdapter(Messages)
        chatRecycler!!.adapter = chatAdapter
    }



}