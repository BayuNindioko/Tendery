package com.example.tendery.ui.postTest.nilai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditJawabanBinding
import com.example.tendery.databinding.ActivityNilaiPostTestBinding

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}