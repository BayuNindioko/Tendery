package com.example.tendery.ui.hps.editHPS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailTenderBinding
import com.example.tendery.databinding.ActivityEdithpsBinding

class EdithpsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdithpsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit User"
        }
        binding = ActivityEdithpsBinding.inflate(layoutInflater)
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