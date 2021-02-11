package cat.copernic.groupz.ui.activities.main.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cat.copernic.groupz.R



class SwipeFragment : Fragment() {
     lateinit var dialog : Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonDialog = view.findViewById<ImageButton>(R.id.btnLike)
        dialog = context?.let { Dialog(it) }!!
        buttonDialog.setOnClickListener {
            openMatchDialog()
        }


    }

    private fun openMatchDialog() {
        dialog.setContentView(R.layout.friendzone_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}