package com.example.tendery.ui.hps.detailHPS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.databinding.ActivityRincianHpsBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity
import com.example.tendery.ui.hps.editHPS.EdithpsActivity

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

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EdithpsActivity::class.java)
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