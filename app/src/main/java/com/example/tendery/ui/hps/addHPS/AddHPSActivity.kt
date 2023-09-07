package com.example.tendery.ui.hps.addHPS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityAddHpsactivityBinding
import com.example.tendery.ui.hps.rv.HpsModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddHPSActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHpsactivityBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Tambah HPS"
        }
        binding = ActivityAddHpsactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("HPS")

        binding.button.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val kodeRup = binding.kodeEditText.text.toString()
        val jenisBarangJasa = binding.jenisBarangEditText.text.toString()
        val satuan = binding.satuanEditText.text.toString()
        val harga = binding.hargaPajakEditText.text.toString()
        val pajak = binding.pajakEditText.text.toString()
        val total = binding.totalEditText.text.toString()
        val keterangan = binding.keteranganEditText.text.toString()
        val nilaiPagu = binding.nilaiPaguEditText.text.toString()
        val dokumenPersiapan = binding.DokumenEditText.text.toString()

        if (kodeRup.isEmpty()|| jenisBarangJasa.isEmpty() || satuan.isEmpty() || harga.isEmpty() || pajak.isEmpty() ||
            total.isEmpty() || keterangan.isEmpty() || nilaiPagu.isEmpty() ||
            dokumenPersiapan.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val HpsId = dbRef.push().key!!
            val hps = HpsModel(HpsId,kodeRup,jenisBarangJasa,satuan,harga,pajak,total,keterangan,nilaiPagu,dokumenPersiapan)

            dbRef.child(HpsId).setValue(hps)
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