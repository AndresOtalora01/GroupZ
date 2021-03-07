package cat.copernic.groupz.ui.activities.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentLoginBinding
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.MainActivity
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            if (checkLoginFields()) {
                binding.btnLogin.visibility = View.GONE
                binding.shimmerViewContainer.visibility = View.VISIBLE
                binding.shimmerViewContainer.startShimmer()
                FirebaseClient.auth.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity( Intent(context, MainActivity::class.java))
                        activity?.finish()
                    } else {
                        binding.shimmerViewContainer.visibility = View.GONE
                        binding.shimmerViewContainer.stopShimmer()
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.etEmail.error = getString(R.string.errorAuthEmailPassword)
                        binding.etPassword.error = getString(R.string.errorAuthEmailPassword)
                    }
                }
            }
        }
        binding.tvSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_register)
        }
        binding.tvforgotPaswort.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_forgotten_password)
        }

    }

    fun checkLoginFields(): Boolean {
        if (binding.etEmail.text.isEmpty() || binding.etPassword.text.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                binding.etEmail.text.toString()
            ).matches()) {
            if (binding.etEmail.text.isEmpty()) {
                binding.etEmail.error = getString(R.string.errorEmptyField)

            }else{
                binding.etEmail.error = getString(R.string.errorNotValidMail)
            }
            if (binding.etPassword.text.isEmpty()) {
                binding.etPassword.error = getString(R.string.errorEmptyField)

            }
            return false
        }

        return true
    }

}
