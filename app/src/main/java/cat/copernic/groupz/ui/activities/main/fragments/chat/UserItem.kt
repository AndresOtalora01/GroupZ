package cat.copernic.groupz.ui.activities.main.fragments.chat

import cat.copernic.groupz.R
import cat.copernic.groupz.model.User
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_profile_nearby.view.*

class UserItem (val user: User,
                val userId:String) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
          viewHolder.itemView.nameProfileNearby.text = user.name
          viewHolder.itemView.tvProfileLocation.text = user.location
          viewHolder.itemView.birthProfileNearby.text = user.birth
          Glide.with(viewHolder.itemView.context).load(user.image)
              .into(viewHolder.itemView.profileImageNearby)
    }

    override fun getLayout() = R.layout.row_profile_nearby

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2

}