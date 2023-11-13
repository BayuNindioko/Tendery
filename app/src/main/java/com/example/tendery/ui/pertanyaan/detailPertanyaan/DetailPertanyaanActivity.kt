package com.example.tendery.ui.pertanyaan.detailPertanyaan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailPenawaranBinding
import com.example.tendery.databinding.ActivityDetailPertanyaanBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddJawabanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailPertanyaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPertanyaanBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Rincian Pertanyaan"
        }
        binding = ActivityDetailPertanyaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

//        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())
//
//        userDocRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    val role = document.getString("Role")
//                    if (role == "Penyedia" || role == "PPK") {
//
//                    }else {
//
//                    }
//                } else {
//
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT,).show()


        val id = intent.getStringExtra("pertanyaanId")
        val kodeTender = intent.getStringExtra("kodeTender")
        val kodePertanyaan = intent.getStringExtra("kodePertanyaan")
        val pertanyaan = intent.getStringExtra("pertanyaan")

        binding.textViewKodeTender.text = kodeTender
        binding.textViewPenanya.text    = "Kode Pertanyaan       :" + " " + kodePertanyaan
        binding.textViewDetailPertanyaan.text = pertanyaan
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}