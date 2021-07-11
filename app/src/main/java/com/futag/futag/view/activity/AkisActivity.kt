package com.futag.futag.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.futag.futag.R
import com.futag.futag.databinding.ActivityAkisBinding

class AkisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAkisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.evFragment, R.id.blogFragment, R.id.etkinlikFragment,R.id.profilFragment,R.id.dahaFragment))

        setupActionBarWithNavController(navController,appBarConfiguration)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

}