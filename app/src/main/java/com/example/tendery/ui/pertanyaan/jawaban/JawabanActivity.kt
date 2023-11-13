package com.example.tendery.ui.pertanyaan.jawaban

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailPenawaranBinding
import com.example.tendery.databinding.ActivityJawabanBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddJawabanActivity
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore

class JawabanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJawabanBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Jawaban"
        }
        binding = ActivityJawabanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "PPK" || role == "Penyedia") {
                        binding.floatingActionButton4.visibility = View.GONE
                    }else {
                        binding.floatingActionButton4.visibility = View.VISIBLE
                    }
                } else {
                    binding.floatingActionButton4.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }

        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(this, AddJawabanActivity::class.java)
            startActivity(intent)
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