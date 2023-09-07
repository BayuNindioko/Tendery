package com.example.tendery.ui.penawaran.addPenawaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityAddPenawaranBinding
import com.example.tendery.ui.penawaran.rv.PenawaranModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPenawaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPenawaranBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Penawaran"
        }
        binding = ActivityAddPenawaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Penawaran")

        binding.button.setOnClickListener {
            submitData()
        }

    }

    private fun submitData() {
        val kode = binding.kodeEditText.text.toString()
        val harga = binding.hargaEditText.text.toString()
        val dokumen = binding.dokumenEditText.text.toString()
        val status = "Tersedia"
        if (kode.isEmpty()|| harga.isEmpty() || dokumen.isEmpty() ) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val PenawaranId = dbRef.push().key!!
            val penawaran = PenawaranModel(PenawaranId,harga,kode,dokumen,status)

            dbRef.child(PenawaranId).setValue(penawaran)
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