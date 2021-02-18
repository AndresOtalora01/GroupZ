package cat.copernic.groupz.ui.activities.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentForgottenPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgottenPasswordFragment : Fragment() {
   private lateinit var binding: FragmentForgottenPasswordBinding
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
        return inflater.inflate(R.layout.fragment_forgotten_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =  FragmentForgottenPasswordBinding.bind(view)
        binding.btnRegister.setOnClickListener {
            val email = binding.etRecuEmail.text.toString()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Confirmado", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}