package cat.copernic.groupz.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import cat.copernic.groupz.R
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.main.MainActivity

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_home)
        setupActionBarWithNavController(findNavController(R.id.home_fragment_container))
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.home_fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}