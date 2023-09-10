package com.example.tendery.ui.preTest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityPreTestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PreTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreTestBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var nilai = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Pre Test"
        }

        binding = ActivityPreTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.jawaban1c.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }

        binding.jawaban2a.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban3a.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban4b.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban5c.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban6b.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban7a.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban8b.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban9c.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }
        binding.jawaban10a.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                nilai += 10
            }
        }

        binding.button.setOnClickListener {
            submitNilai(nilai)

        }
    }

    private fun submitNilai(nilai: Int) {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser
        binding.progressBar7.visibility = View.VISIBLE
        if (currentUser != null) {
            val uid = currentUser.uid
            val userDocRef = fStore.collection("Users").document(uid)

            userDocRef.update("PreTest", nilai)
                .addOnSuccessListener {
                    val intent = Intent(this, NilaiPreTestActivity::class.java)
                    intent.putExtra("nilaiPreTest", nilai.toString())
                    startActivity(intent)
                    finish()
                    binding.progressBar7.visibility = View.GONE
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT).show()
                    binding.progressBar7.visibility = View.GONE
                }

        } else {
            Toast.makeText(this, "Coba Lagi!", Toast.LENGTH_SHORT).show()
            binding.progressBar7.visibility = View.GONE
        }
    }
}