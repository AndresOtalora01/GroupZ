package cat.copernic.groupz.ui.activities.home.fragments
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentLogOutBinding
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.home.HomeActivity
import cat.copernic.groupz.ui.activities.main.MainActivity
import com.google.firebase.auth.FirebaseAuth



class LogOutFragment : Fragment() {
    private lateinit var binding: FragmentLogOutBinding
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
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLogOutBinding.bind(view)
        builder = AlertDialog.Builder(context) //Preparamos el Alert dialog
        builder.setTitle("Log Out")
        builder.setPositiveButton("Aceptar", null)
        binding.btnLogOut.setOnClickListener {
            FirebaseClient.auth.signOut()
            builder.setMessage(R.string.LogOut);
            val dialog = builder.create()
            dialog.show()

            startActivity( Intent(context, HomeActivity::class.java))
        }
        binding.btnDelAccount.setOnClickListener{
            findNavController().navigate(R.id.action_logOutFragment_to_deleteConfirmationFragment)

        }

    }
}