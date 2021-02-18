package cat.copernic.groupz.ui.activities.main.fragments.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

class GroupsAdapter (val titles : List<String>, val images : List<Int>): RecyclerView.Adapter<MyViewHolder>(){

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.image.setImageResource(images[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.groups_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return titles.size
    }

}

class MyViewHolder (v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

    var title : TextView = v.findViewById(R.id.tvGroupName)
    var image : ImageView = v.findViewById(R.id.ivGroupImage)
    override fun onClick(p0: View?) {

    }
}