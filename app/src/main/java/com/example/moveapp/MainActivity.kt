package com.example.moveapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moveapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {
        navController = findNavController(R.id.nav_controller_view)
//        setupActionBarWithNavController(navController)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu)
        val menu = popupMenu.menu

        binding.bottomNavigation.setupWithNavController(menu, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun viewGone() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun viewVisiblite() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
    }
}