package cat.copernic.groupz.ui.activities.main.fragments.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

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
            itemImage =  itemView.findViewById(R.id.categoryItemImage)
            itemTitle = itemView.findViewById(R.id.tvCategoryItemTitle)
            itemDestination = itemView.findViewById(R.id.tvCategoryItemDestination)
            itemLocation = itemView.findViewById(R.id.tvProfileLocation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.itemImage.setImageResource(categoryItems[position].imageUrl)
        holder.itemTitle.text = categoryItems[position].eventTitle
        holder.itemDestination.text = categoryItems[position].eventType
        holder.itemLocation.text = categoryItems[position].location
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }

}