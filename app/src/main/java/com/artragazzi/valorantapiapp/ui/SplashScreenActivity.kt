package com.artragazzi.valorantapiapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.artragazzi.valorantapiapp.R
import com.artragazzi.valorantapiapp.databinding.ActivitySplashScreenBinding
import com.bumptech.glide.Glide

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.main.alpha = 0f
        binding.main.animate().setDuration(4000).alpha(1f).withEndAction {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        Glide.with(this)
            .load(R.drawable.carregando)
            .into(binding.ivCarregando)


    }
}