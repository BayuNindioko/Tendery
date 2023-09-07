package com.example.tendery.ui.hps.editHPS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import android.widget.Toast

import com.example.tendery.databinding.ActivityEdithpsBinding
import com.example.tendery.ui.hps.rv.HpsModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EdithpsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdithpsBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit User"
        }
        binding = ActivityEdithpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("HpsId")
        val kodeRup = intent.getStringExtra("kodeRup")
        val jenisBarangJasa = intent.getStringExtra("jenisBarangJasa")
        val satuan = intent.getStringExtra("satuan")
        val harga = intent.getStringExtra("harga")
        val pajak = intent.getStringExtra("pajak")
        val total = intent.getStringExtra("total")
        val keterangan = intent.getStringExtra("keterangan")
        val nilaiPagu = intent.getStringExtra("nilaiPagu")
        val dokumenPersiapan = intent.getStringExtra("dokumenPersiapan")

        binding.namaEditText.setText(kodeRup)
        binding.jenisBarangEditText.setText(jenisBarangJasa)
        binding.satuanEditText.setText(satuan)
        binding.hargaPajakEditText.setText(harga)
        binding.pajakEditText.setText(pajak)
        binding.totalEditText.setText(total)
        binding.keteranganEditText.setText(keterangan)
        binding.nilaiPaguEditText.setText(nilaiPagu)
        binding.DokumenEditText.setText(dokumenPersiapan)

        binding.button.setOnClickListener {
            updateData(id)
        }

    }


    private fun updateData(id: String?) {
        val kodeRup = binding.namaEditText.text.toString()
        val jenisBarangJasa = binding.jenisBarangEditText.text.toString()
        val satuan = binding.satuanEditText.text.toString()
        val harga = binding.hargaPajakEditText.text.toString()
        val pajak = binding.pajakEditText.text.toString()
        val total = binding.totalEditText.text.toString()
        val keterangan = binding.keteranganEditText.text.toString()
        val nilaiPagu = binding.nilaiPaguEditText.text.toString()
        val dokumenPersiapan = binding.DokumenEditText.text.toString()

        val dbRef = FirebaseDatabase.getInstance().getReference("HPS").child(id.toString())
        val empInfo = HpsModel(id, kodeRup,jenisBarangJasa,satuan,harga,pajak,total,keterangan,nilaiPagu,dokumenPersiapan)
        dbRef.setValue(empInfo)
        Toast.makeText(applicationContext, "Data Berhasil Diperbarui!", Toast.LENGTH_LONG).show()

        val updatedIntent = Intent()
        updatedIntent.putExtra("updatedKodeRup", kodeRup)
        updatedIntent.putExtra("updatedJenisBarangJasa", jenisBarangJasa)
        updatedIntent.putExtra("updatedSatuan", satuan)
        updatedIntent.putExtra("updatedHarga", harga)
        updatedIntent.putExtra("updatedPajak", pajak)
        updatedIntent.putExtra("updatedTotal", total)
        updatedIntent.putExtra("updatedKeterangan", keterangan)
        updatedIntent.putExtra("updatedNilaiPagu", nilaiPagu)
        updatedIntent.putExtra("updatedDokumenPersiapan", dokumenPersiapan)


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