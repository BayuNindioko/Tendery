package com.example.tendery.ui.preTest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.tendery.MainActivity
import com.example.tendery.R
import com.example.tendery.databinding.ActivityNilaiPreTestBinding

class NilaiPreTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNilaiPreTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Hasil Test"
        }
        binding = ActivityNilaiPreTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nilaiPreTest = intent.getStringExtra("nilaiPreTest")
        val nilai = nilaiPreTest?.toIntOrNull() ?: 0

        binding.nilaiTextView.text = nilai.toString()

        if (nilai < 50) {
            binding.nilaiTextView.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.nilaiTextView.setTextColor(ContextCompat.getColor(this, R.color.green))
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}