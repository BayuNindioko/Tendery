package com.example.tendery.ui.penawaran.addPenawaran

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.databinding.ActivityAddPenawaranBinding
import com.example.tendery.ui.penawaran.rv.PenawaranModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddPenawaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPenawaranBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    var dokumen =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Penawaran"
        }
        binding = ActivityAddPenawaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef = FirebaseStorage.getInstance().getReference()
        dbRef = FirebaseDatabase.getInstance().getReference("Penawaran")

        binding.button.setOnClickListener {
            submitData()
        }

        binding.button4.setOnClickListener {
            selectFile()
        }

    }

    private fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select PDF Files..."), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            uploadFiles(data.data!!)
        }
    }

    private fun uploadFiles(data: Uri) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        val reference = storageRef.child("Penawaran/${System.currentTimeMillis()}.pdf")

        reference.putFile(data)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl

                uriTask.addOnSuccessListener { uri ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    val url = uriTask.result
                    dokumen = url.toString()
                }
            }
            .addOnProgressListener { snapshot ->
                val progress = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
                progressDialog.setMessage("Uploaded: ${progress.toInt()}%")
            }

    }


    private fun submitData() {
        val kode = binding.kodeEditText.text.toString()
        val harga = binding.hargaEditText.text.toString()
        val dokumenLink = dokumen

        val status = "Tersedia"
        if (kode.isEmpty()|| harga.isEmpty() ) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar4.visibility = View.VISIBLE
            val PenawaranId = dbRef.push().key!!
            val penawaran = PenawaranModel(PenawaranId,kode,harga,dokumenLink,status)

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