package cat.copernic.groupz.ui.activities.main.fragments.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.ui.activities.main.fragments.events.MainRecyclerAdapter

class NotificationsAdapter(
    private var titles: List<String>,
    private var descriptions: List<String>,
    private var images: List<Int>,
    private var timeAgo: List<String>
) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tvNotificationTitle)
        val itemDescription: TextView = itemView.findViewById(R.id.tvNotificationDescription)
        val itemImage: ImageView = itemView.findViewById(R.id.tvNotificationImage)
        val timeAgo: TextView = itemView.findViewById(R.id.tvNotificationsTimeAgo)
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDescription.text = descriptions[position]
        holder.itemImage.setImageResource(images[position])
        holder.timeAgo.text = timeAgo[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }

}