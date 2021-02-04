package cat.copernic.groupz.ui.activities.main.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)

        binding.sendButton.setOnClickListener{


        }

    }

}