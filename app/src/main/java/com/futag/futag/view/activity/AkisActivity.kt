package com.futag.futag.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.futag.futag.databinding.ActivityAkisBinding
import kotlin.system.exitProcess

class AkisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAkisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

    // Geri tusuyla uygualamadan direkt cikma, aksi takdirde 2 kez sayfa yuklenmesi oluyor
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        exitProcess(0)
    }

}