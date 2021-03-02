package cat.copernic.groupz.ui.activities.main.fragments.events

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class CategoryItemAdapter(
    private val context: Context,
    private val categoryItems: List<CategoryItem>
) : RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {

    class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDestination: TextView
        var itemLocation: TextView

        init {
            itemImage =  itemView.findViewById(R.id.ivGroupImage)
            itemTitle = itemView.findViewById(R.id.tvGroupName)
            itemDestination = itemView.findViewById(R.id.tvEventDate)
            itemLocation = itemView.findViewById(R.id.tvCategoryItemLocation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        Glide.with(context)
            .load(categoryItems[position].imageUrl)
            .placeholder(R.drawable.animated_progress)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.itemImage)
        holder.itemTitle.text = categoryItems[position].eventTitle
        holder.itemDestination.text = categoryItems[position].eventDate
        holder.itemLocation.text = categoryItems[position].location
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }

}