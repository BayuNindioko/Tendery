package com.example.tendery.ui.hps.editHPS

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import android.widget.Toast
import com.example.tendery.databinding.ActivityEdithpsBinding
import com.example.tendery.ui.hps.rv.HpsModel
import com.example.tendery.ui.penawaran.rv.PenawaranModel

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EdithpsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdithpsBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    var dokumenPersiapan = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit HPS"
        }
        binding = ActivityEdithpsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storageRef = FirebaseStorage.getInstance().reference

        val id = intent.getStringExtra("HpsId")
        val kodeRup = intent.getStringExtra("kodeRup")
        val jenisBarangJasa = intent.getStringExtra("jenisBarangJasa")
        val satuan = intent.getStringExtra("satuan")
        val harga = intent.getStringExtra("harga")
        val pajak = intent.getStringExtra("pajak")
        val total = intent.getStringExtra("total")
        val keterangan = intent.getStringExtra("keterangan")
        val nilaiPagu = intent.getStringExtra("nilaiPagu")
        dokumenPersiapan = intent.getStringExtra("dokumenPersiapan") ?: ""

        binding.namaEditText.setText(kodeRup)
        binding.jenisBarangEditText.setText(jenisBarangJasa)
        binding.satuanEditText.setText(satuan)
        binding.hargaPajakEditText.setText(harga)
        binding.pajakEditText.setText(pajak)
        binding.totalEditText.setText(total)
        binding.keteranganEditText.setText(keterangan)
        binding.nilaiPaguEditText.setText(nilaiPagu)


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

        val reference = storageRef.child("HPS/${System.currentTimeMillis()}.pdf")

        reference.putFile(data)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl

                uriTask.addOnSuccessListener { uri ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    val url = uriTask.result
                    dokumenPersiapan = url.toString()
                }
            }
            .addOnProgressListener { snapshot ->
                val progress = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
                progressDialog.setMessage("Uploaded: ${progress.toInt()}%")
            }

    }

    private fun updateData(id: String?) {
        val kodeRup = binding.namaEditText.text.toString()
        val jenisBarangJasa = binding.jenisBarangEditText.text.toString()
        val satuan = binding.satuanEditText.text.toString()
        val harga = binding.hargaPajakEditText.text.toString()
        val pajak = binding.pajakEditText.text.toString()
        val total = binding.totalEditText.text.toString()
        val keterangan = binding.keteranganEditText.text.toString()
        val nilaiPagu = binding.nilaiPaguEditText.text.toString()
        val dokumenPersiapanUpdate = dokumenPersiapan

        val dbRef = FirebaseDatabase.getInstance().getReference("HPS").child(id.toString())
        val empInfo = HpsModel(id, kodeRup,jenisBarangJasa,satuan,harga,pajak,total,keterangan,nilaiPagu,dokumenPersiapanUpdate)
        dbRef.setValue(empInfo)
        Toast.makeText(applicationContext, "Data Berhasil Diperbarui!", Toast.LENGTH_LONG).show()

        val updatedIntent = Intent()
        updatedIntent.putExtra("updatedKodeRup", kodeRup)
        updatedIntent.putExtra("updatedJenisBarangJasa", jenisBarangJasa)
        updatedIntent.putExtra("updatedSatuan", satuan)
        updatedIntent.putExtra("updatedHarga", harga)
        updatedIntent.putExtra("updatedPajak", pajak)
        updatedIntent.putExtra("updatedTotal", total)
        updatedIntent.putExtra("updatedKeterangan", keterangan)
        updatedIntent.putExtra("updatedNilaiPagu", nilaiPagu)
        updatedIntent.putExtra("updatedDokumenPersiapan", dokumenPersiapan)


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