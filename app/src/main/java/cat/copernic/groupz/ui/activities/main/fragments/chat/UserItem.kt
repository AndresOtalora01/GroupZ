package cat.copernic.groupz.ui.activities.main.fragments.chat

import cat.copernic.groupz.R
import cat.copernic.groupz.model.User
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.row_profile_nearby.view.*
import java.util.*

class UserItem (val user: User,
                val userId:String) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
          viewHolder.itemView.nameProfileNearby.text = user.name+","
          viewHolder.itemView.tvProfileLocation.text = user.location
        var date = user.birth.split("/").map {
            it.trim()
        }
        var rdate = Calendar.getInstance().get(Calendar.DATE) - date.get(0).toInt()
        var rmonth = (Calendar.getInstance().get(Calendar.MONTH) + 1) - date.get(1).toInt()
        var ryear = Calendar.getInstance().get(Calendar.YEAR) - date.get(2).toInt()
        if (rmonth >= 0 && rdate >= 0) {
            viewHolder.itemView.birthProfileNearby.text = ryear.toString()
        } else{
            viewHolder.itemView.birthProfileNearby.text = (ryear-1).toString()
        }
          Glide.with(viewHolder.itemView.context).load(user.image)
              .into(viewHolder.itemView.profileImageNearby)
    }

    override fun getLayout() = R.layout.row_profile_nearby

    override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 2

}