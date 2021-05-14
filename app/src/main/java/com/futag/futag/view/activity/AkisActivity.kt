package com.futag.futag.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.futag.futag.databinding.ActivityAkisBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.system.exitProcess

class AkisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAkisBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        binding.button.setOnClickListener {
            auth.signOut()
            finish()
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        exitProcess(0)
    }

}