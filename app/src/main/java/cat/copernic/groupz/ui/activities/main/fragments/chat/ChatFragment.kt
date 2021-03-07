package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatBinding
import cat.copernic.groupz.model.Message
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.network.FirestoreUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*
import kotlin.collections.ArrayList


class ChatFragment : Fragment() {
    // private var chatRecycler: RecyclerView? = null
    // private var chatAdapter: ChatAdapter? = null
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private val args by navArgs<ChatFragmentArgs>()
    private lateinit var binding: FragmentChatBinding
    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var messagesSection: Section
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        binding = FragmentChatBinding.bind(view)

        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        btndrawerLayout.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility =
            View.GONE
        activity?.findViewById<CardView>(R.id.imagechatTulbar)!!.visibility = View.VISIBLE
        val imageChat = activity?.findViewById<ImageView>(R.id.imagechat)!!
        val titleToolbar = activity?.findViewById<TextView>(R.id.tvTittleToolBar)
        val data = FirebaseClient.db.collection("Users").document(args.userId)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    titleToolbar!!.text = it.get("Name") as String
                    Glide.with(this)
                        .load(it.get("Image").toString())
                        .placeholder(R.drawable.animated_progress)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageChat)
                }
            }

        val otherUserId = args.userId
        FirestoreUtil.getOrCreateChatChannel(otherUserId) { channelId ->
            messagesListenerRegistration =
                FirestoreUtil.addChatMessageListener(channelId, this::updateRecyclerView)
            ivSendMessage.setOnClickListener {
                if (!etMessage.text.toString().isEmpty()) {
                    val messageToSend =
                        TextMessage(
                            etMessage.text.toString(),
                            Calendar.getInstance().time,
                            FirebaseAuth.getInstance().currentUser!!.uid,
                            MessageType.TEXT
                        )
                    etMessage.setText("")
                    FirestoreUtil.sendMessage(messageToSend, channelId)
                }
            }
        }
        return binding.root
    }

    private fun updateRecyclerView(messages: List<Item>) {
        fun init() {
            rvMessages.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    messagesSection = Section(messages)
                    this.add(messagesSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messagesSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        rvMessages.scrollToPosition(rvMessages.adapter!!.itemCount - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.findViewById<CardView>(R.id.imagechatTulbar)!!.visibility = View.GONE
        FirestoreUtil.removeListener(messagesListenerRegistration)
        shouldInitRecyclerView = true
    }

}