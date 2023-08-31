package com.example.tendery.ui.hps.addHPS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddHpsactivityBinding
import com.example.tendery.databinding.ActivityEditAkunBinding

class AddHPSActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHpsactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Tambah HPS"
        }
        binding = ActivityAddHpsactivityBinding.inflate(layoutInflater)
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