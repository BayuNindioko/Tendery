package com.example.tendery.ui.postTest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditJawabanBinding
import com.example.tendery.databinding.ActivityPostTestBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.postTest.nilai.NilaiPostTestActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PostTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostTestBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var nilai = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Post Test"
        }
        binding = ActivityPostTestBinding.inflate(layoutInflater)
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

            userDocRef.update("PostTest", nilai)
                .addOnSuccessListener {
                    val intent = Intent(this, NilaiPostTestActivity::class.java)
                    intent.putExtra("nilaiPostTest", nilai.toString())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}