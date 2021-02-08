package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.net.sip.SipSession
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

class ChatListAdapter(private val list: List<ChatListRow>, private val listener : OnItemClickListener) : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chats_list,parent,false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = list[position]
        holder.profileImage.setImageResource(currentItem.profileImage)
        holder.chatName.text = currentItem.chatName
        holder.lastMessage.text = currentItem.lastMessage
    }


    override fun getItemCount() = list.size

    inner class ChatListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        val chatName: TextView = itemView.findViewById(R.id.chatName)
        val lastMessage: TextView = itemView.findViewById(R.id.lastMessage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)

    }

}