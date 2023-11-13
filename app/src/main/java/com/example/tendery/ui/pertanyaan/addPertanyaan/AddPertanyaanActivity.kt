package com.example.tendery.ui.pertanyaan.addPertanyaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPenawaranBinding
import com.example.tendery.databinding.ActivityAddPertanyaanBinding
import com.example.tendery.ui.penawaran.rv.PenawaranModel
import com.example.tendery.ui.pertanyaan.rv.PertanyaanModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddPertanyaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPertanyaanBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Pertanyaan"
        }
        binding = ActivityAddPertanyaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef = FirebaseStorage.getInstance().getReference()
        dbRef = FirebaseDatabase.getInstance().getReference("Pertanyaan")

        binding.button.setOnClickListener {
            submitData()
        }

    }

    private fun submitData() {
        val kodeTender = binding.kodeEditText.text.toString()
        val kodePertanyaan = binding.kodePertanyaanEditText.text.toString()
        val pertanyaan = binding.pertanyaanEditText.text.toString()


        if (kodeTender.isEmpty()|| kodePertanyaan.isEmpty()|| pertanyaan.isEmpty() ) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val PertanyaanId = dbRef.push().key!!
            val penawaran = PertanyaanModel(PertanyaanId,kodeTender,kodePertanyaan,pertanyaan)

            dbRef.child(PertanyaanId).setValue(penawaran)
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