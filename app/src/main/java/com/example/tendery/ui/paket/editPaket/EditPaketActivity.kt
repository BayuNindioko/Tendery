package com.example.tendery.ui.paket.editPaket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityEditPaketBinding
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.ui.paket.PaketFragment
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.paket.rv.PaketModel
import com.google.firebase.database.FirebaseDatabase

class EditPaketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPaketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.daftar_pengguna)
        }
        binding = ActivityEditPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("paketid")
        val nama = intent.getStringExtra("paketNama")
        val kodeRup = intent.getStringExtra("paketKodeRup")
        val klpd = intent.getStringExtra("paketKlpd")
        val nilaiPagu = intent.getStringExtra("paketNilaiPagu")
        val tahun = intent.getStringExtra("paketTahun")
        val jenisPengadaan = intent.getStringExtra("paketJenisPengadaan")
        val provinsi = intent.getStringExtra("paketProvinsi")
        val kabupatenKota = intent.getStringExtra("paketKabupatenKota")
        val lokasiLengkap = intent.getStringExtra("paketLokasiLengkap")

        binding.namaEditText.setText(nama)
        binding.kodeEditText.setText(kodeRup)
        binding.klpdEditText.setText(klpd)
        binding.nilaiPaguEditText.setText(nilaiPagu)
        binding.tahunEditText.setText(tahun)
        binding.jenisPengadaanEditText.setText(jenisPengadaan)
        binding.provinsiEditText.setText(provinsi)
        binding.kabupatenKotaEditText.setText(kabupatenKota)
        binding.lokasiLengkapEditText.setText(lokasiLengkap)

        binding.button.setOnClickListener {
            updateData(id)
        }

    }

    private fun updateData(id:String?) {
        val nama = binding.namaEditText.text.toString()
        val kodeRup = binding.kodeEditText.text.toString()
        val klpd = binding.klpdEditText.text.toString()
        val nilaiPagu = binding.nilaiPaguEditText.text.toString()
        val tahun = binding.tahunEditText.text.toString()
        val jenisPengadaan = binding.jenisPengadaanEditText.text.toString()
        val provinsi = binding.provinsiEditText.text.toString()
        val kabupatenKota = binding.kabupatenKotaEditText.text.toString()
        val lokasiLengkap = binding.lokasiLengkapEditText.text.toString()


        val dbRef = FirebaseDatabase.getInstance().getReference("Paket_Tender").child(id.toString())
        val empInfo = PaketModel(id, nama,kodeRup,klpd,nilaiPagu,tahun,jenisPengadaan,provinsi,kabupatenKota,lokasiLengkap)
        dbRef.setValue(empInfo)
        Toast.makeText(applicationContext, "Data Berhasil Diperbarui!", Toast.LENGTH_LONG).show()
        val fragment = PaketFragment() // Buat objek fragment
        val transaction = supportFragmentManager.beginTransaction() // Memulai transaksi fragment
        transaction.replace(R.id.editpaket, fragment) // Ganti kontainer dengan fragment
        transaction.addToBackStack(null) // (Opsional) Tambahkan transaksi ke back stack
        transaction.commit()
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