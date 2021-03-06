package cat.copernic.groupz.ui.activities.home.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.FragmentDeleteConfirmationBinding
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


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
//            val data = FirebaseClient.db.collection("Users").document(user?.email!!)
//            data.get()
//                .addOnSuccessListener {
//                    if (it.get("Image").toString()!="https://firebasestorage.googleapis.com/v0/b/groupz-c793a.appspot.com/o/imageProfile%2FDefault_profile.png?alt=media&token=108bdb29-c173-48ad-807d-31c3d2a5ce0e"){
//                        var storage_ref = FirebaseStorage.getInstance().getReferenceFromUrl(it.get("Image").toString())
//                        storage_ref.delete()
//                    }
//
//                    data.delete()
//                }
            user?.delete()
            openDeletedAccountDialog()

            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(context, HomeActivity::class.java))
                activity?.finish()
            }, 2000)

        }
    }

    private fun openDeletedAccountDialog() {
        dialog.setContentView(R.layout.dialog_deleted_account)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}