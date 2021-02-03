package cat.copernic.groupz.ui.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatsListBinding


class ChatsListFragment : Fragment() {
    private lateinit var binding: FragmentChatsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatsListBinding.bind(view)


        binding.btToChat.setOnClickListener{
            findNavController().navigate(R.id.action_chatsListFragment_to_chatFragment)
        }

    }

    //Falta programar internamente la recogida de datos de los chats.



}