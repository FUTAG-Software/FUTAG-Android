package com.futag.futag.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.futag.futag.databinding.ActivityAkisBinding

class AkisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAkisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAkisBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}