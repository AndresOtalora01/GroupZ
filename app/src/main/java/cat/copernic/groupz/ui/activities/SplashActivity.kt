package cat.copernic.groupz.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import cat.copernic.groupz.R
import cat.copernic.groupz.network.FirebaseClient
import cat.copernic.groupz.ui.activities.home.HomeActivity
import cat.copernic.groupz.ui.activities.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseClient.userLogIn()){
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        }, SPLASH_TIME)

    }
}