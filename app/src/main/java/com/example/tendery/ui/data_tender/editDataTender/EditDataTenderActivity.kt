package com.example.tendery.ui.data_tender.editDataTender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.databinding.ActivityEditDataTenderBinding
import com.example.tendery.ui.data_tender.rv.DataModel
import com.google.firebase.database.FirebaseDatabase

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

        val id = intent.getStringExtra("DataId")
        val nama = intent.getStringExtra("nama")
        val kodeTender = intent.getStringExtra("kodeTender")
        val jenisKualifikasi = intent.getStringExtra("jenisKualifikasi")
        val keterangan = intent.getStringExtra("keterangan")
        val mulai = intent.getStringExtra("mulai")
        val selesai = intent.getStringExtra("selesai")

        binding.namaEditText.setText(nama)
        binding.kodeEditText.setText(kodeTender)
        binding.jenisKualifikasiEditText.setText(jenisKualifikasi)
        binding.keteranganKualifikasiEditText.setText(keterangan)
        binding.tanggalMulaiEditText.setText(mulai)
        binding.tanggalSelesaiEditText.setText(selesai)

        binding.button.setOnClickListener {
            updateData(id)
        }
    }

    private fun updateData(id: String?) {
        val nama = binding.namaEditText.text.toString()
        val kode = binding.kodeEditText.text.toString()
        val jenisKualifikasi = binding.jenisKualifikasiEditText.text.toString()
        val keteranganKualifikasi = binding.keteranganKualifikasiEditText.text.toString()
        val tanggalMulai = binding.tanggalMulaiEditText.text.toString()
        val tanggalSelesai = binding.tanggalSelesaiEditText.text.toString()

        val dbRef = FirebaseDatabase.getInstance().getReference("Data_Tender").child(id.toString())
        val dataInfo = DataModel(id, nama,kode,jenisKualifikasi,keteranganKualifikasi,tanggalMulai,tanggalSelesai)
        dbRef.setValue(dataInfo)
        Toast.makeText(applicationContext, "Data Berhasil Diperbarui!", Toast.LENGTH_LONG).show()

        val updatedIntent = Intent()
        updatedIntent.putExtra("updateNama", nama)
        updatedIntent.putExtra("updateKodeTender", kode)
        updatedIntent.putExtra("updateJenisKualifikasi", jenisKualifikasi)
        updatedIntent.putExtra("updateKeterangan", keteranganKualifikasi)
        updatedIntent.putExtra("updateMulai", tanggalMulai)
        updatedIntent.putExtra("updateSelesai", tanggalSelesai)

        setResult(RESULT_OK, updatedIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}