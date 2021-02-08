package cat.copernic.groupz.ui.activities.main.fragments.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentDeleteConfirmationBinding
import cat.copernic.groupz.databinding.FragmentGroupsBinding


class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
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

    }
}