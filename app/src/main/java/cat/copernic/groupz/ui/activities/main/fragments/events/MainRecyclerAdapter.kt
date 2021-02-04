package cat.copernic.groupz.ui.activities.main.fragments.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

class MainRecyclerAdapter(
    private val context: Context?,
    private val allCategories: List<AllCategories>
) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTitle: TextView
        var itemRecyclerView: RecyclerView

        init {
            categoryTitle = itemView.findViewById(R.id.categoryTitle)
            itemRecyclerView = itemView.findViewById(R.id.categoryItemRecycler)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.main_events_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.categoryTitle.text = allCategories[position].categoryTitle
        setCategoryItemRecycler(holder.itemRecyclerView, allCategories[position].categoryItems)
    }

    override fun getItemCount(): Int {
        return allCategories.size
    }

    private fun setCategoryItemRecycler(
        recyclerView: RecyclerView,
        categoryItem: List<CategoryItem>
    ) {
        val itemRecyclerAdapter = CategoryItemAdapter(context!!, categoryItem)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = itemRecyclerAdapter
    }
}