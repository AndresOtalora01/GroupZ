package cat.copernic.groupz.ui.activities.main.fragments.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R


class MainEventsFragment : Fragment() {
    private var mainCategoryRecycler: RecyclerView? = null
    private var mainRecyclerAdapter: MainRecyclerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_events, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainCategoryRecycler = view?.findViewById(R.id.mainRecyclerEvents)

        //primera categoria de prueba
        val categoryItemList : MutableList<CategoryItem> = ArrayList()
        categoryItemList.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
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


        //quinta categoría de prueba
        val categoryItemList5 : MutableList<CategoryItem> = ArrayList()
        categoryItemList5.add(CategoryItem(R.drawable.pedra, "Paseo", "Montaña del destino", "Valencia"))
        categoryItemList5.add(CategoryItem(R.drawable.pedra, "Baile", "Salón recreacional", "Madrid"))
        categoryItemList5.add(CategoryItem(R.drawable.pedra, "Torneo", "Estadio", "Barcelona"))
        categoryItemList5.add(CategoryItem(R.drawable.pedra, "Paseo", "Centro comercial", "Zurich"))
        categoryItemList5.add(CategoryItem(R.drawable.pedra, "Spiderman 3", "Cines", "Andorra"))



        val allCategories: MutableList<AllCategories> = ArrayList()
        allCategories.add(AllCategories("Destacados", categoryItemList))
        allCategories.add(AllCategories("Por tiempo limitado", categoryItemList2))
        allCategories.add(AllCategories("Para toda la familia",categoryItemList3))
        allCategories.add(AllCategories("Musicales", categoryItemList4))
        allCategories.add(AllCategories("En pareja", categoryItemList5))
        setMainCategoryRecycler(allCategories)
    }

    private fun setMainCategoryRecycler(allCategories: List<AllCategories>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        mainCategoryRecycler!!.layoutManager = layoutManager
        mainRecyclerAdapter = MainRecyclerAdapter(context, allCategories)
        mainCategoryRecycler!!.adapter = mainRecyclerAdapter
    }






}