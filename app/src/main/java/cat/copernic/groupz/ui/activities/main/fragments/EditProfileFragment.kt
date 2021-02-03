package cat.copernic.groupz.ui.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    var userAuth = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        loadData()

        binding.btnSave.setOnClickListener{
            if (saveData()){
                findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)

            }

        }
    }

    fun loadData(){
        userAuth?.email

        binding.etNameEdit.setText("Manolo")
        binding.etDateEdit.setText("20/01/1965")
        binding.etHobbieEdit.setText("Futbol, Futbol")
        binding.etDescriptionEdit.setText("Me gusta el futbol jaja")

    }

    fun saveData() : Boolean{

        return true
    }


}