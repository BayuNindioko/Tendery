package com.example.tendery.ui.pertanyaan.addPertanyaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddJawabanBinding
import com.example.tendery.databinding.ActivityAddPenawaranBinding

class AddJawabanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddJawabanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Jawaban"
        }
        binding = ActivityAddJawabanBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}