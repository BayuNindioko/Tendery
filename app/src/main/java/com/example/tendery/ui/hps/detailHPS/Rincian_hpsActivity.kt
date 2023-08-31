package com.example.tendery.ui.hps.detailHPS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.databinding.ActivityRincianHpsBinding

class Rincian_hpsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRincianHpsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Rincian HPS"
        }
        binding = ActivityRincianHpsBinding.inflate(layoutInflater)
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