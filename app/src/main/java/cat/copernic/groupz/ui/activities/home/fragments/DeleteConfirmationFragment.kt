package cat.copernic.groupz.ui.activities.home.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
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
    lateinit var dialog : Dialog
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
        dialog = context?.let { Dialog(it) }!!
        binding.btnNo.setOnClickListener{
            findNavController().navigate(R.id.action_deleteConfirmationFragment_to_logOutFragment)
        }
        binding.btnYes.setOnClickListener{
            val user =  FirebaseAuth.getInstance().currentUser

            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        openDeletedAccountDialog()
                    }
                }

            val handler = Handler()
            handler.postDelayed({
                startActivity( Intent(context, HomeActivity::class.java))
            }, 2000)
        }
    }

    private fun openDeletedAccountDialog() {
        dialog.setContentView(R.layout.dialog_deleted_account)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

}