package cat.copernic.groupz.ui.activities.main.fragments.chat


import android.content.Context
import cat.copernic.groupz.R
import cat.copernic.groupz.model.User
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_chats_list.view.*

class ChatListItem (val user: User, val channelId : String, val context: Context ) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.chatName.text = user.name
        Glide.with(context)
            .load(user.image)
            .placeholder(R.drawable.animated_progress)
            .into(viewHolder.itemView.profileImage)
    }

    override fun getLayout() = R.layout.row_chats_list

}