package com.example.tendery.ui.pertanyaan.detailPertanyaan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailPenawaranBinding
import com.example.tendery.databinding.ActivityDetailPertanyaanBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddJawabanActivity

class DetailPertanyaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPertanyaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Rincian Pertanyaan"
        }
        binding = ActivityDetailPertanyaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val intent = Intent(this, AddJawabanActivity::class.java)
            startActivity(intent)
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