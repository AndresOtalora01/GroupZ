package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.model.Message
import cat.copernic.groupz.network.FirebaseClient

//class ChatAdapter(private val Messages : List<Message>) :RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

//
//    class ChatViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
//        val layoutMessageFrom: ConstraintLayout = itemView.findViewById(R.id.messageRoot)
//       // val layoutMessageTo: ConstraintLayout = itemView.findViewById(R.id.layoutMessageTo)
//        val tvMessageFrom: TextView = itemView.findViewById(R.id.tvMessageText)
//        val tvMessageTo: TextView = itemView.findViewById(R.id.tvMessageTo)
//        val ivImageFrom: ImageView = itemView.findViewById(R.id.ivImageFrom)
//        val ivImageTo: ImageView = itemView.findViewById(R.id.ivImageTo)
//
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_message_chat,parent,false)
//        return ChatViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
//        val currentItem = Messages[position]
//
//        if(FirebaseClient.auth.currentUser?.email as String == currentItem.from){
//            holder.layoutMessageFrom.visibility = View.VISIBLE
//            holder.layoutMessageTo.visibility = View.GONE
//
//            holder.tvMessageFrom.text = currentItem.message
//        } else {
//            holder.layoutMessageFrom.visibility = View.GONE
//            holder.layoutMessageTo.visibility = View.VISIBLE
//
//            holder.tvMessageTo.text = currentItem.message
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return Messages.size
//    }



//}