package cat.copernic.groupz.ui.activities.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import cat.copernic.groupz.R

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_home)

    }
}