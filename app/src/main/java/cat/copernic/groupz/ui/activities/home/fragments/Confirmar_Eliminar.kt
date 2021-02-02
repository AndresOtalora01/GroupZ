package cat.copernic.groupz.ui.activities.home.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentConfirmarEliminarBinding
import com.google.firebase.auth.FirebaseAuth


class Confirmar_Eliminar : Fragment() {
    private lateinit var binding: FragmentConfirmarEliminarBinding
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
        return inflater.inflate(R.layout.fragment_confirmar__eliminar, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmarEliminarBinding.bind(view)
        builder = AlertDialog.Builder(context) //Preparamos el Alert dialog
        builder.setTitle("Log Out")
        builder.setPositiveButton("Aceptar", null)
        binding.btnNo.setOnClickListener{

            findNavController().navigate(R.id.action_logOutFragment_to_login)
        }
        binding.btnSi.setOnClickListener{
            val user =  FirebaseAuth.getInstance().currentUser

            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        builder.setMessage(R.string.accountDeleted);
                        val dialog = builder.create()
                        dialog.show()
                        Log.d(TAG, "User account deleted.")
                    }
                }
            findNavController().navigate(R.id.action_logOutFragment_to_login)
        }
    }

}