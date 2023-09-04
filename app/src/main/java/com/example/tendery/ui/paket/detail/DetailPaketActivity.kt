package com.example.tendery.ui.paket.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityDetailPaketBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity
import com.example.tendery.ui.paket.editPaket.EditPaketActivity

class DetailPaketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPaketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ("Detail Paket Tender")
        }
        binding = ActivityDetailPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditPaketActivity::class.java)
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