package com.example.tendery.ui.data_tender.editDataTender

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditAkunBinding
import com.example.tendery.databinding.ActivityEditDataTenderBinding
import com.example.tendery.ui.data_tender.rv.DataModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditDataTenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataTenderBinding
    private lateinit var storageRef: StorageReference
    var dokumenTender = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit Data Tender"
        }
        binding = ActivityEditDataTenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storageRef = FirebaseStorage.getInstance().reference

        val id = intent.getStringExtra("DataId")
        val nama = intent.getStringExtra("nama")
        val kodeTender = intent.getStringExtra("kodeTender")
        val jenisKualifikasi = intent.getStringExtra("jenisKualifikasi")
        val keterangan = intent.getStringExtra("keterangan")
        val mulai = intent.getStringExtra("mulai")
        val selesai = intent.getStringExtra("selesai")
        dokumenTender = intent.getStringExtra("dokumenTender") ?: ""

        binding.namaEditText.setText(nama)
        binding.kodeEditText.setText(kodeTender)
        binding.jenisKualifikasiEditText.setText(jenisKualifikasi)
        binding.keteranganKualifikasiEditText.setText(keterangan)
        binding.tanggalMulaiEditText.setText(mulai)
        binding.tanggalSelesaiEditText.setText(selesai)

        binding.button.setOnClickListener {
            updateData(id)
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

        val reference = storageRef.child("Data Tender/${System.currentTimeMillis()}.pdf")

        reference.putFile(data)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl

                uriTask.addOnSuccessListener { uri ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    val url = uriTask.result
                    dokumenTender = url.toString()
                }
            }
            .addOnProgressListener { snapshot ->
                val progress = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
                progressDialog.setMessage("Uploaded: ${progress.toInt()}%")
            }

    }

    private fun updateData(id: String?) {
        val nama = binding.namaEditText.text.toString()
        val kode = binding.kodeEditText.text.toString()
        val jenisKualifikasi = binding.jenisKualifikasiEditText.text.toString()
        val keteranganKualifikasi = binding.keteranganKualifikasiEditText.text.toString()
        val tanggalMulai = binding.tanggalMulaiEditText.text.toString()
        val tanggalSelesai = binding.tanggalSelesaiEditText.text.toString()
        val dokumenTenderUpdate = dokumenTender.toString()

        val dbRef = FirebaseDatabase.getInstance().getReference("Data_Tender").child(id.toString())
        val dataInfo = DataModel(id, nama,kode,jenisKualifikasi,keteranganKualifikasi,tanggalMulai,tanggalSelesai,dokumenTenderUpdate)
        dbRef.setValue(dataInfo)
        Toast.makeText(applicationContext, "Data Berhasil Diperbarui!", Toast.LENGTH_LONG).show()

        val updatedIntent = Intent()
        updatedIntent.putExtra("updateNama", nama)
        updatedIntent.putExtra("updateKodeTender", kode)
        updatedIntent.putExtra("updateJenisKualifikasi", jenisKualifikasi)
        updatedIntent.putExtra("updateKeterangan", keteranganKualifikasi)
        updatedIntent.putExtra("updateMulai", tanggalMulai)
        updatedIntent.putExtra("updateSelesai", tanggalSelesai)
        updatedIntent.putExtra("updatedDokumenTender", dokumenTenderUpdate)

        setResult(RESULT_OK, updatedIntent)
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