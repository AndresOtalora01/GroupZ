package cat.copernic.groupz.ui.activities.main.fragments.events

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentMainEventsBinding
import cat.copernic.groupz.model.Event
import cat.copernic.groupz.network.FirebaseClient
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainEventsFragment : Fragment() {
    private var mainCategoryRecycler: RecyclerView? = null
    private var mainRecyclerAdapter: MainRecyclerAdapter? = null
    private lateinit var binding: FragmentMainEventsBinding
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private var allCategories: MutableList<AllCategories> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_events, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainEventsBinding.bind(view)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.events)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE

        binding.btnShowEnevt.setOnClickListener {
            findNavController().navigate(R.id.action_mainEventsFragment_to_showEventFragment)
        }
        binding.fabMyEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mainEventsFragment_to_mayEventsFragment)
        }

        mainCategoryRecycler = view.findViewById(R.id.mainRecyclerEvents)

        //primera categoria de prueba
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Paseo", "11/09/2020","Valencia"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))

        //segunda categoria de prueba
        val categoryItemList2 : MutableList<CategoryItem> = ArrayList()
        categoryItemList2.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
        categoryItemList2.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList2.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList2.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList2.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))


        //tercera categoría de prueba
        val categoryItemList3 : MutableList<CategoryItem> = ArrayList()
        categoryItemList3.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
        categoryItemList3.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList3.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList3.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList3.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))

        //cuarta categoria de prueba
        val categoryItemList4 : MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))



        allCategories.add(AllCategories("Destacados", categoryItemList))
        allCategories.add(AllCategories("Por tiempo limitado", categoryItemList2))
        allCategories.add(AllCategories("Para toda la familia",categoryItemList3))
        allCategories.add(AllCategories("Musicales", categoryItemList4))
        getDatabaseCommunityEvents()
        setMainCategoryRecycler(allCategories)

    }

    private fun setMainCategoryRecycler(allCategories: List<AllCategories>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        mainCategoryRecycler!!.layoutManager = layoutManager
        mainRecyclerAdapter = MainRecyclerAdapter(context, allCategories)
        mainCategoryRecycler!!.adapter = mainRecyclerAdapter
    }
    fun getDatabaseCommunityEvents() {
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        var resultEvent = arrayListOf<Event>()
        var data = FirebaseClient.db.collection("CommunityEvents")
        data.get().addOnCompleteListener { events ->
            for (event in events.result!!) {
                if (event.data["Privacity"] as Boolean) {
                    resultEvent.add(
                        Event(
                            event.data["Admin"].toString(),
                            event.data["Date"].toString(),
                            event.data["Description"].toString(),
                            event.data["Location"].toString(),
                            event.data["Members"] as List<String>,
                            event.data["Name"].toString(),
                            event.data["Privacity"] as Boolean
                        )
                    )
                }
            }
            for (event in resultEvent){
                categoryItemList.add(CategoryItem(R.drawable.pedra,event.name,event.date,event.location))
            }
            allCategories.add(AllCategories("Eventos de la comunidad", categoryItemList))


        }

    }









}