package com.example.tendery.ui.paket.addPaket


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.ui.paket.rv.PaketModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPaketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaketBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Paket Tender"
        }
        binding = ActivityAddPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Paket_Tender")

        binding.button.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val nama = binding.namaEditText.text.toString()
        val kodeRup = binding.kodeEditText.text.toString()
        val klpd = binding.klpdEditText.text.toString()
        val nilaiPagu = binding.nilaiPaguEditText.text.toString()
        val tahun = binding.tahunEditText.text.toString()
        val jenisPengadaan = binding.jenisPengadaanEditText.text.toString()
        val provinsi = binding.provinsiEditText.text.toString()
        val kabupatenKota = binding.kabupatenKotaEditText.text.toString()
        val lokasiLengkap = binding.lokasiLengkapEditText.text.toString()

        if (nama.isEmpty()|| kodeRup.isEmpty() || klpd.isEmpty() || nilaiPagu.isEmpty() || tahun.isEmpty() ||
            jenisPengadaan.isEmpty() || provinsi.isEmpty() || kabupatenKota.isEmpty() ||
            lokasiLengkap.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val paketId = dbRef.push().key!!
            val paket = PaketModel(paketId,nama,kodeRup,klpd,nilaiPagu,tahun,jenisPengadaan,provinsi,kabupatenKota,lokasiLengkap)

            dbRef.child(paketId).setValue(paket)
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