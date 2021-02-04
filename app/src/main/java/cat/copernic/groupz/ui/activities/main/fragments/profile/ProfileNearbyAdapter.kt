package cat.copernic.groupz.ui.activities.main.fragments.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

class ProfileNearbyAdapter(private val ProfileNearbyList: List<ProfileNearbyRow>) : RecyclerView.Adapter<ProfileNearbyAdapter.ProfileNearbyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileNearbyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_profile_nearby,parent,false)
        return ProfileNearbyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileNearbyViewHolder, position: Int) {
        val currentItem = ProfileNearbyList[position]
        holder.profileImageNearby.setImageResource(currentItem.profileImageNearby)
        holder.profileNameNearby.text = currentItem.profileNameNearby
        holder.birthProfileNearby.text = currentItem.birthProfileNearby
    }

    override fun getItemCount() = ProfileNearbyList.size

    class ProfileNearbyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val profileImageNearby : ImageView = itemView.findViewById(R.id.profileImageNearby)
        val profileNameNearby : TextView = itemView.findViewById(R.id.nameProfileNearby)
        val birthProfileNearby : TextView = itemView.findViewById(R.id.birthProfileNearby)

    }
}