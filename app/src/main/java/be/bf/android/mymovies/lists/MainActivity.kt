package be.bf.android.mymovies.lists

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import be.bf.android.mymovies.R
import be.bf.android.mymovies.databinding.ActivityMainBinding
import be.bf.android.mymovies.login.LoginActivity

/**
 * Activity that manages the 4 movie list fragments
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.fragmentContainerView2)
        binding.bottomNavigationView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_app_bar_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val preferences: SharedPreferences = getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)

        when (item.itemId) {

            R.id.logout -> {

                preferences.edit().clear().apply()

                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}