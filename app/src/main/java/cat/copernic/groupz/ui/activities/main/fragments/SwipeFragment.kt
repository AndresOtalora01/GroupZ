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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.fragment.app.Fragment
import cat.copernic.groupz.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class SwipeFragment : Fragment() {
     lateinit var dialog : Dialog
    private lateinit var btndrawerLayout: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btndrawerLayout = activity?.findViewById(R.id.btnMenu)!!
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout?.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        btndrawerLayout?.visibility = View.VISIBLE
        activity?.findViewById<TextView>(R.id.tvTittleToolBar)?.text = getString(R.string.swipe)
        activity?.findViewById<ImageButton>(R.id.btnBack)!!.visibility = View.GONE
        activity?.findViewById<ImageButton>(R.id.btnNotifications)!!.visibility = View.VISIBLE
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.visibility = View.VISIBLE

        var buttonDialog = view.findViewById<ImageButton>(R.id.btnLike)
        dialog = context?.let { Dialog(it) }!!
        buttonDialog.setOnClickListener {
            openMatchDialog()
        }

    }

    private fun openMatchDialog() {
        dialog.setContentView(R.layout.dialog_friendzone_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

}