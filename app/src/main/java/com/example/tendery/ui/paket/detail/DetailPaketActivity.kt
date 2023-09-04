package com.example.tendery.ui.paket.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityDetailPaketBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity
import com.example.tendery.ui.paket.editPaket.EditPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.common.returnsreceiver.qual.This

class DetailPaketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPaketBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ("Detail Paket Tender")
        }
        binding = ActivityDetailPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia" || role == "Pemberi Jasa") {
                        binding.fabEdit.visibility = View.GONE
                        binding.fabDelete.visibility = View.GONE
                    }else {
                        binding.fabEdit.visibility = View.VISIBLE
                        binding.fabDelete.visibility = View.VISIBLE
                    }
                } else {
                    binding.fabEdit.visibility = View.VISIBLE
                    binding.fabDelete.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }
        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditPaketActivity::class.java)
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