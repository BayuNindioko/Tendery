package com.example.tendery.ui.admin.mainAdmin.ui.pertanyaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.tendery.databinding.ActivityEditSoalBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditSoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditSoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit Soal"
        }

        val soal = intent.getStringExtra("soal")
        val pilihanA = intent.getStringExtra("pilihanA")
        val pilihanB = intent.getStringExtra("pilihanB")
        val pilihanC = intent.getStringExtra("pilihanC")
        val jawaban = intent.getStringExtra("jawaban")

        binding.soalEditText.setText(soal)
        binding.AEditText.setText(pilihanA)
        binding.BEditText.setText(pilihanB)
        binding.CEditText.setText(pilihanC)
        binding.jawabanEditText.setText(jawaban)

        binding.buttonEdit.setOnClickListener{
            editSoal()
        }
    }

    private fun editSoal() {
        val editedSoal = binding.soalEditText.text.toString()
        val editedPilihanA = binding.AEditText.text.toString()
        val editedPilihanB = binding.BEditText.text.toString()
        val editedPilihanC = binding.CEditText.text.toString()
        val editedJawaban = binding.jawabanEditText.text.toString()


        val nomorSoal = intent.getIntExtra("nomorSoal", 1)


        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Test").document("nrctuyVsbfg2xZleMTJA")

        docRef.update("list_soal.soal$nomorSoal.soal", editedSoal)
        docRef.update("list_soal.soal$nomorSoal.A", editedPilihanA)
        docRef.update("list_soal.soal$nomorSoal.B", editedPilihanB)
        docRef.update("list_soal.soal$nomorSoal.C", editedPilihanC)
        docRef.update("list_soal.soal$nomorSoal.jawaban", editedJawaban)
        Toast.makeText(this, "Soal Berhasil di Update", Toast.LENGTH_SHORT).show()
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