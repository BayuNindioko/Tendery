package com.example.tendery.ui.pertanyaan.jawaban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailJawabanBinding
import com.example.tendery.databinding.ActivityJawabanBinding

class DetailJawabanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailJawabanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Jawaban"
        }
        binding = ActivityDetailJawabanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kodePertanyaan = intent.getStringExtra("KODE_PERTANYAAN")
        val pertanyaan = intent.getStringExtra("PERTANYAAN")

        // Menampilkan string di TextView atau komponen yang sesuai
        binding.textViewKodePertanyaan.text = "Kode Pertanyaan : " + kodePertanyaan
        binding.textViewDetailjawaban.text = pertanyaan


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}