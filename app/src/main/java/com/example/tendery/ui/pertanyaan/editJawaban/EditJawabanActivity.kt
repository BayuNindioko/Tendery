package com.example.tendery.ui.pertanyaan.editJawaban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPenawaranBinding
import com.example.tendery.databinding.ActivityEditJawabanBinding

class EditJawabanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditJawabanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Edit Jawaban"
        }
        binding = ActivityEditJawabanBinding.inflate(layoutInflater)
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