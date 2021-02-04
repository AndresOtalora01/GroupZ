package cat.copernic.groupz.ui.activities.main.fragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.ui.activities.main.fragments.events.AllCategories
import cat.copernic.groupz.ui.activities.main.fragments.events.MainRecyclerAdapter


class NotificationsFragment : Fragment() {
    private var notificationsRecyclerView: RecyclerView? = null
    private var notificationRecyclerAdapter: NotificationsAdapter? = null
    private var titlesList = mutableListOf<String>()
    private var descriptionsList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    private var timeAgoList =  mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postToList()
        notificationsRecyclerView = view.findViewById(R.id.rvNotifications)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        notificationsRecyclerView!!.layoutManager = layoutManager
        notificationRecyclerAdapter = NotificationsAdapter(titlesList, descriptionsList,imagesList,timeAgoList)
        notificationsRecyclerView!!.adapter = notificationRecyclerAdapter
    }

    private fun addToList(title:String, description:String, image:Int, timeAgo: String) {
        titlesList.add(title)
        descriptionsList.add(description)
        imagesList.add(image)
        timeAgoList.add(timeAgo)
    }

    private fun postToList() {
        for(i in 1..25) {
            addToList("Invitación a grupo", "María te ha invitado a su grupo de lectura", R.drawable.pedra, "2 min")
        }
    }
}