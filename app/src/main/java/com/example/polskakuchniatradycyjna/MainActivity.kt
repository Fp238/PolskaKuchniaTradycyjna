package com.example.polskakuchniatradycyjna

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.polskakuchniatradycyjna.databinding.ActivityMainBinding
import com.example.polskakuchniatradycyjna.viewmodel.OrderViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


        orderViewModel.currentOrder.observe(this, Observer { updateTotalBar() })
        orderViewModel.totalSum.observe(this, Observer { updateTotalBar() })
    }

    private fun updateTotalBar() {
        val currentSum = orderViewModel.getCurrentOrderSum()
        val totalSum = orderViewModel.totalSum.value ?: 0.0

        binding.totalBar.text =
            "Suma obecnego zamówienia: ${"%.2f".format(currentSum)} zł | Łącznie: ${"%.2f".format(totalSum)} zł"
    }
}