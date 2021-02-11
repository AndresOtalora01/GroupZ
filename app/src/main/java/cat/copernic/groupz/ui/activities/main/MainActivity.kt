package cat.copernic.groupz.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import cat.copernic.groupz.R
import cat.copernic.groupz.databinding.ActivityMainBinding
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
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(navigationView,navController)
        
    }

}