package cat.copernic.groupz.ui.activities.home.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentDeleteConfirmationBinding
import cat.copernic.groupz.ui.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth


class DeleteConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentDeleteConfirmationBinding
    private lateinit var builder: AlertDialog.Builder
    var TAG : String = "Log auth Fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_confirmation, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeleteConfirmationBinding.bind(view)
        builder = AlertDialog.Builder(context) //Preparamos el Alert dialog
        builder.setTitle("Log Out")
        builder.setPositiveButton("Aceptar", null)
        binding.btnNo.setOnClickListener{


            findNavController().navigate(R.id.action_deleteConfirmationFragment_to_logOutFragment)
        }
        binding.btnYes.setOnClickListener{
            val user =  FirebaseAuth.getInstance().currentUser

            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        builder.setMessage(R.string.accountDeleted);
                        val dialog = builder.create()
                        dialog.show()
                        Log.d(TAG, "User account deleted.").minus(1)

                    }
                }
            startActivity( Intent(context, HomeActivity::class.java))


        }
    }

}