package cat.copernic.groupz.ui.activities.main.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentCreateEventBinding


class CreateEventFragment : Fragment() {
    private lateinit var binding: FragmentCreateEventBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateEventBinding.bind(view)
        val toggleButtonPublic = binding.toggleButtonPublic
        val toggleButtonPrivate = binding.toggleButtonPrivate
        toggleButtonPublic.isChecked = true;
        toggleButtonPublic.text = getString(R.string.public_)
        toggleButtonPublic.textOff = getString(R.string.public_)
        toggleButtonPublic.textOn = getString(R.string.public_)
        toggleButtonPrivate.text = getString(R.string.private_)
        toggleButtonPrivate.textOff = getString(R.string.private_)
        toggleButtonPrivate.textOn = getString(R.string.private_)
        toggleButtonPrivate.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            toggleButtonPublic.isChecked = !isChecked
        })
        toggleButtonPublic.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            toggleButtonPrivate.isChecked = !isChecked
        })
    }


}