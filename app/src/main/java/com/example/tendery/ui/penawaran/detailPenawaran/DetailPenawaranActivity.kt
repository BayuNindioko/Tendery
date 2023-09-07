package com.example.tendery.ui.penawaran.detailPenawaran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityDetailPenawaranBinding
import com.example.tendery.ui.hps.rv.HpsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DetailPenawaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPenawaranBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Rincian Penawaran"
        }
        binding = ActivityDetailPenawaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia" ||role == "PPK") {
                        binding.pilihPenawaran.visibility = View.GONE
                        binding.fabDelete.visibility = View.GONE
                    }else{
                        binding.pilihPenawaran.visibility = View.VISIBLE
                        binding.fabDelete.visibility = View.VISIBLE
                    }
                } else {
                    binding.pilihPenawaran.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }


        val id = intent.getStringExtra("penawaranId")
        val kodePenawaran = intent.getStringExtra("kodePenawaran")
        val hargaPenawaran = intent.getStringExtra("hargaPenawaran")
        val dokumenPenawaran = intent.getStringExtra("dokumenPenawaran")

        getDetail(kodePenawaran,hargaPenawaran,dokumenPenawaran)

        binding.fabDelete.setOnClickListener {
            binding.progressBar4.visibility = View.VISIBLE
            val dbRef = FirebaseDatabase.getInstance().getReference("Penawaran").child(id.toString())
            val mTask = dbRef.removeValue()

            mTask.addOnSuccessListener {
                binding.progressBar4.visibility = View.GONE
                Toast.makeText(this, "Data Berhasil Dihapus!", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.pilihPenawaran.setOnClickListener {
            val dbRef = FirebaseDatabase.getInstance().getReference("Penawaran").child(id.toString())
            val status = "Dipilih"


            val updateData = mapOf("status" to status)

            dbRef.updateChildren(updateData)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Penawaran Dipilih!", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener { error ->
                    Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun getDetail(
        kodePenawaran: String?,
        hargaPenawaran: String?,
        dokumenPenawaran: String?
    ) {
        binding.textViewKodeTender.text = "Kode Tender           :" + " " + kodePenawaran
        binding.textViewHarga.text      = "Harga Penawaran :" + " " + hargaPenawaran
        binding.textViewDokumen.text = "Dokumen " + " " + dokumenPenawaran
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}