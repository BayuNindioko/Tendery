package com.example.tendery.ui.data_tender.editDataTender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.databinding.ActivityEditDataTenderBinding

class EditDataTenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataTenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit Data Tender"
        }
        binding = ActivityEditDataTenderBinding.inflate(layoutInflater)
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