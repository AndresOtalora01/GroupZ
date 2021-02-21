package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.model.ChatListRow
import java.util.*
import kotlin.collections.ArrayList

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>(), Filterable {

    private lateinit var chatFilterList: List<ChatListRow>
    private lateinit var chatList: List<ChatListRow>
    private lateinit var listener: OnItemClickListener
     fun setData(chatList: List<ChatListRow>, listener: OnItemClickListener) {
        this.chatList = chatList
        this.listener = listener
        chatFilterList = chatList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_chats_list, parent, false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chatList[position]

        holder.profileImage.setImageResource(currentItem.profileImage)
        holder.chatName.text = currentItem.chatName
        holder.lastMessage.text = currentItem.lastMessage
    }

    override fun getItemCount() = chatList.size

    inner class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
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
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val filterResults = FilterResults()
                if (charSearch.isEmpty() || charSearch.length < 0) {
                    filterResults.count = chatFilterList.size
                    filterResults.values = chatFilterList
                } else {
                    val resultList = arrayListOf<ChatListRow>()
                    for (row in chatFilterList) {
                        if (row.chatName.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) ||
                            row.lastMessage.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterResults.count = resultList.size
                    filterResults.values = resultList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                chatList = results?.values as List<ChatListRow>
                notifyDataSetChanged()
            }
        }
    }
}

