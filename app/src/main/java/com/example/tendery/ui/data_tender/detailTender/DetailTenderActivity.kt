package com.example.tendery.ui.data_tender.detailTender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailPaketBinding
import com.example.tendery.databinding.ActivityDetailTenderBinding
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity

class DetailTenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Rincian Tender"
        }
        binding = ActivityDetailTenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditDataTenderActivity::class.java)
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