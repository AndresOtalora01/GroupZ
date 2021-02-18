package cat.copernic.groupz.ui.activities.main.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R

class MyEventsFragment : Fragment() {
    private var mainCategoryRecycler: RecyclerView? = null
    private var mainRecyclerAdapter: CategoryItemAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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