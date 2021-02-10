package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatBinding
import cat.copernic.groupz.model.ChatListRow
import cat.copernic.groupz.model.Message
import cat.copernic.groupz.network.FirebaseClient
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {
    private var chatRecycler: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null


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

        chatRecycler = view.findViewById(R.id.messagesView)
        val categoryItemList : MutableList<Message> = ArrayList()
        categoryItemList.add(Message("Hola", "Manolo"))
        categoryItemList.add(Message("Hola", FirebaseClient.auth.currentUser?.email as String))
        categoryItemList.add(Message("Como Estas?", "Manolo"))
        setChatRecycler(categoryItemList)

        binding.sendButton.setOnClickListener{
            binding.messageText.setText("")
        }
    }

    private fun setChatRecycler(Messages: List<Message>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        chatRecycler!!.layoutManager = layoutManager
        chatAdapter = ChatAdapter(Messages)
        chatRecycler!!.adapter = chatAdapter
    }

    fun sendMessage(){

    }

}