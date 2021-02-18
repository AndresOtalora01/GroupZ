package cat.copernic.groupz.ui.activities.main.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentMainEventsBinding
import cat.copernic.groupz.databinding.FragmentMyEventsBinding

class MyEventsFragment : Fragment() {
    private var mainCategoryRecycler: RecyclerView? = null
    private var mainRecyclerAdapter: CategoryItemAdapter? = null
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: FragmentMyEventsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyEventsBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        btndrawerLayout?.visibility = View.GONE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.my_events)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.VISIBLE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.GONE

        binding.fabCreateEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mayEventsFragment_to_createEventFragment)
        }

        mainCategoryRecycler = view.findViewById(R.id.mayEventsList)
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))

        setMainCategoryRecycler(categoryItemList)
    }



    private fun setMainCategoryRecycler(categoryItemList: List<CategoryItem>) {
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        mainCategoryRecycler!!.layoutManager = layoutManager
        mainRecyclerAdapter = context?.let { CategoryItemAdapter(it, categoryItemList) }
        mainCategoryRecycler!!.adapter = mainRecyclerAdapter
    }


}