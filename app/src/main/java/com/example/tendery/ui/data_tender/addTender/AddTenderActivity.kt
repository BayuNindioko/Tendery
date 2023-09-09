package com.example.tendery.ui.data_tender.addTender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityAddTenderBinding
import com.example.tendery.ui.data_tender.rv.DataModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddTenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTenderBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Tambah Tender"
        }
        binding = ActivityAddTenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Data_Tender")

        binding.button.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val nama = binding.namaEditText.text.toString()
        val kodeTender = binding.kodeEditText.text.toString()
        val jenisKualifikasi= binding.jenisKualifikasiEditText.text.toString()
        val keterangan = binding.keteranganKualifikasiEditText.text.toString()
        val tanggalMulai = binding.tanggalMulaiEditText.text.toString()
        val tanggalSelesai = binding.tanggalSelesaiEditText.text.toString()

        if (nama.isEmpty()||kodeTender.isEmpty()|| jenisKualifikasi.isEmpty() || keterangan.isEmpty() || tanggalMulai.isEmpty() || tanggalSelesai.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val dataId = dbRef.push().key!!
            val data = DataModel(dataId,nama,kodeTender,jenisKualifikasi,keterangan,tanggalMulai,tanggalSelesai)

            dbRef.child(dataId).setValue(data)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data Berhasil Ditambah!", Toast.LENGTH_LONG).show()
                    finish()
                    binding.progressBar4.visibility = View.GONE
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    binding.progressBar4.visibility = View.GONE
                }
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