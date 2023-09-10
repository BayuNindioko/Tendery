package com.example.tendery.ui.postTest.nilai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditJawabanBinding
import com.example.tendery.databinding.ActivityNilaiPostTestBinding
import com.example.tendery.ui.postTest.PostTestFragment

class NilaiPostTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNilaiPostTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Nilai Post Test"
        }
        binding = ActivityNilaiPostTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nilaiPostTest = intent.getStringExtra("nilaiPostTest")
        val nilai = nilaiPostTest?.toIntOrNull() ?: 0

        binding.nilaiTextView.text = nilai.toString()

        if (nilai < 50) {
            binding.nilaiTextView.setTextColor(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.nilaiTextView.setTextColor(ContextCompat.getColor(this, R.color.green))
        }

        binding.button3.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}