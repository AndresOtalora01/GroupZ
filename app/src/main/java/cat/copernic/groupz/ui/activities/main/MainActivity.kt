package cat.copernic.groupz.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.ActivityMainBinding
import cat.copernic.groupz.network.FirebaseClient
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
        navheadrview.findViewById<TextView>(R.id.tvUserEmail).text = FirebaseClient.auth.currentUser?.email
        navheadrview.findViewById<ImageButton>(R.id.btnClouseNav).setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navigationView,navController)
        
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}