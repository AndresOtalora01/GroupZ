package cat.copernic.groupz.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.ActivityMainBinding
import cat.copernic.groupz.network.FirebaseClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.main_nav_host_fragment)
        val navigationView = binding.mainNavigationView
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainEventsFragment,
                R.id.nearbyPeopleFragment,
                R.id.swipeFragment,
                R.id.chatsListFragment,
                R.id.groupsFragment,
            )
        )
        val  navheadrview = navigationView.getHeaderView(0)
        val user = FirebaseClient.auth.currentUser?.email
        navheadrview.findViewById<TextView>(R.id.tvUserEmail).text = user
        val data = FirebaseClient.db.collection("Users").document(user!!)
        data.get()
            .addOnSuccessListener {
                if (it != null) {
                    Glide.with(this)
                        .load(it.get("Image").toString())
                        .placeholder(R.drawable.animated_progress)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(navheadrview.findViewById(R.id.ivProfile))
                }

            }
        navheadrview.findViewById<ImageButton>(R.id.btnClouseNav).setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navigationView,navController)
        binding.btnNotifications.setOnClickListener {
            findNavController(R.id.main_nav_host_fragment).navigate(R.id.notificationsFragment)
        }
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}