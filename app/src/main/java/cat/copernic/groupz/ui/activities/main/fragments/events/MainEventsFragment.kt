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
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import okio.Okio.source


class MainEventsFragment : Fragment() {
    private var mainCategoryRecycler: RecyclerView? = null
    private var mainRecyclerAdapter: MainRecyclerAdapter? = null
    private lateinit var binding: FragmentMainEventsBinding
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private  lateinit var shimmerContainer: ShimmerFrameLayout
    lateinit var  allCategories: MutableList<AllCategories>
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
        binding.shimmerViewContainer.startShimmer()

        binding.btnShowEnevt.setOnClickListener {
            findNavController().navigate(R.id.action_mainEventsFragment_to_showEventFragment)
        }
        binding.fabMyEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mainEventsFragment_to_mayEventsFragment)
        }

        mainCategoryRecycler = view.findViewById(R.id.mainRecyclerEvents)

        //cuarta categoria de prueba
//        val categoryItemList4 : MutableList<CategoryItem> = ArrayList()
//        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
//        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
//        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
//        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
//        categoryItemList4.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))


        allCategories = ArrayList()
        getDatabaseCommunityEvents()
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
                if (!(event.data["Privacity"] as Boolean)) {
                    resultEvent.add(
                        Event(
                            event.data["Admin"].toString(),
                            event.data["Date"].toString(),
                            event.data["Description"].toString(),
                            event.data["Image"].toString(),
                            event.data["Location"].toString(),
                            event.data["Members"] as List<String>,
                            event.data["Name"].toString(),
                            event.data["Privacity"] as Boolean
                        )
                    )
                }
            }
            for (event in resultEvent){
                categoryItemList.add(CategoryItem(event.image,event.name,event.date,event.location))
            }
            allCategories.add(AllCategories("Comunidad", categoryItemList))
            getDatabaseSocialEvents()
        }

    }

    private  fun getDatabaseSportsEvents() {
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        var resultEvent = arrayListOf<Event>()
        var counter = 0
        var data = FirebaseClient.db.collection("SportsEvents")
        data.get().addOnCompleteListener { events ->
            for (event in events.result!!) {
                Log.d("dsusdfuisdbfksdifb", "${event.id} => ${event.data}")
                var members = event.data["Members"] as List<String>
                resultEvent.add(
                    Event(
                        event.data["Admin"].toString(),
                        event.data["Date"].toString(),
                        event.data["Description"].toString(),
                        event.data["Image"].toString(),
                        event.data["Location"].toString(),
                        members,
                        event.data["Name"].toString(),
                        event.data["Privacity"] as Boolean

                    )
                )
            }
            counter = 1
            for (event in resultEvent){
                Log.d("hola", "onViewCreated: ${event.name} ")
                    categoryItemList.add(CategoryItem(event.image,event.name,event.date,event.location))
            }
            allCategories.add(AllCategories("Deportes", categoryItemList))
            getDatabaseMusicEvents()
        }
    }


    private fun getDatabaseSocialEvents() {
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        var resultEvent = arrayListOf<Event>()
        var counter = 0
        var data = FirebaseClient.db.collection("SocialEvents")
        data.get().addOnCompleteListener { events ->
            for (event in events.result!!) {
                Log.d("dsusdfuisdbfksdifb", "${event.id} => ${event.data}")
                var members = event.data["Members"] as List<String>

                resultEvent.add(
                    Event(
                        event.data["Admin"].toString(),
                        event.data["Date"].toString(),
                        event.data["Description"].toString(),
                        event.data["Image"].toString(),
                        event.data["Location"].toString(),
                        members,
                        event.data["Name"].toString(),
                        event.data["Privacity"] as Boolean
                    )
                )

            }

            counter = 1
            for (event in resultEvent){
                Log.d("hola", "onViewCreated: ${event.name} ")
                categoryItemList.add(CategoryItem(event.image,event.name,event.date,event.location))
            }
            allCategories.add(AllCategories("Social", categoryItemList))
            getDatabaseSportsEvents()
        }
    }

    private fun getDatabaseMusicEvents() {
       val categoryItemList : MutableList<CategoryItem> = ArrayList()
        var resultEvent = arrayListOf<Event>()
        var counter = 0
        var data = FirebaseClient.db.collection("MusicEvents")
        data.get().addOnCompleteListener { events ->
            for (event in events.result!!) {
                Log.d("dsusdfuisdbfksdifb", "${event.id} => ${event.data}")
                var members = event.data["Members"] as List<String>

                resultEvent.add(
                    Event(
                        event.data["Admin"].toString(),
                        event.data["Date"].toString(),
                        event.data["Description"].toString(),
                        event.data["Image"].toString(),
                        event.data["Location"].toString(),
                        members,
                        event.data["Name"].toString(),
                        event.data["Privacity"] as Boolean
                    )
                )

            }

            counter = 1
            for (event in resultEvent){
                Log.d("hola", "onViewCreated: ${event.name} ")
                categoryItemList.add(CategoryItem(event.image,event.name,event.date,event.location))
            }
          allCategories.add(AllCategories("Music", categoryItemList))
            setMainCategoryRecycler(allCategories)
            binding.shimmerViewContainer.visibility = View.GONE
            mainCategoryRecycler!!.visibility = View.VISIBLE
        }
    }










}