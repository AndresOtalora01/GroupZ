package cat.copernic.groupz.ui.activities.main.fragments.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentDeleteConfirmationBinding
import cat.copernic.groupz.databinding.FragmentGroupsBinding


class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var titles : MutableList<String>
    private lateinit var images : MutableList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)
    binding.fabCreatGroup.setOnClickListener{
        findNavController().navigate(R.id.action_groupsFragment_to_create_GroupFragment)
    }
        setRecyclerView()
    }

    fun setRecyclerView(){
        recyclerView = binding.recyclerViewGroups
        titles = arrayListOf()
        images = arrayListOf()

        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Bikers Barcelona")
        titles.add("Amantes del teatro")
        titles.add("Kotlin lovers")
        titles.add("Gimnasio y más")
        titles.add("Cocineros Terrassa")
        titles.add("Cocineros Terrassa")

        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)
        images.add(R.drawable.pedra)

        recyclerView.adapter = GroupsAdapter(titles, images)
        val manager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = manager

    }
}