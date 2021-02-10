package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.model.ChatListRow

class ChatListAdapter(private val list: List<ChatListRow>, private val listener : OnItemClickListener) : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    //lateinit var chatFilterList : List<ChatListRow>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_chats_list,parent,false)
        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        //val currentItem = chatFilterList[position]
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
            //chatFilterList = list
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
   /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    chatFilterList = list
                } else {
                    val resultList = MutableList<ChatListRow>()
                    for (row in list) {
                        if (row.chatName.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    chatFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }

        }
    }*/


}