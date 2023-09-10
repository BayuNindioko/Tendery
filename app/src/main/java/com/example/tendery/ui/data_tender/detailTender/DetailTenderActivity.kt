package com.example.tendery.ui.data_tender.detailTender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tendery.R
import com.example.tendery.databinding.ActivityDetailTenderBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity
import com.example.tendery.ui.hps.detailHPS.Rincian_hpsActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DetailTenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTenderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    companion object {
        const val EDIT_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Rincian Tender"
        }
        binding = ActivityDetailTenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia"|| role == "PPK") {
                        binding.fabEdit.visibility = View.GONE
                        binding.fabDelete.visibility = View.GONE
                    }else{
                        binding.fabEdit.visibility = View.VISIBLE
                        binding.fabDelete.visibility = View.VISIBLE
                    }
                } else {
                    binding.fabEdit.visibility = View.VISIBLE
                    binding.fabDelete.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT).show()
            }


        val id = intent.getStringExtra("DataId")
        val nama = intent.getStringExtra("nama")
        val kodeTender = intent.getStringExtra("kodeTender")
        val jenisKualifikasi = intent.getStringExtra("jenisKualifikasi")
        val keterangan = intent.getStringExtra("keterangan")
        val mulai = intent.getStringExtra("mulai")
        val selesai = intent.getStringExtra("selesai")
        val dokumen = intent.getStringExtra("dokumenTender")

        getDetail(nama,kodeTender,jenisKualifikasi,keterangan,mulai,selesai)

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditDataTenderActivity::class.java)
            intent.putExtra("DataId", id)
            intent.putExtra("nama", nama)
            intent.putExtra("kodeTender", kodeTender)
            intent.putExtra("jenisKualifikasi", jenisKualifikasi)
            intent.putExtra("keterangan", keterangan)
            intent.putExtra("mulai", mulai)
            intent.putExtra("selesai", selesai)
            intent.putExtra("dokumenTender", dokumen)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        binding.fabDelete.setOnClickListener {
            binding.progressBar4.visibility = View.VISIBLE
            val dbRef = FirebaseDatabase.getInstance().getReference("Data_Tender").child(id.toString())
            val mTask = dbRef.removeValue()

            mTask.addOnSuccessListener {
                binding.progressBar4.visibility = View.GONE
                Toast.makeText(this, "Data Berhasil Dihapus!", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.download.setOnClickListener {
            val dokumenLink = dokumen
            Log.d("dokuemnTender", dokumenLink.toString())

            if (dokumenLink != null && dokumenLink.isNotEmpty()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(dokumenLink))
                startActivity(browserIntent)
            } else {
                Toast.makeText(this, "Tidak ada tautan dokumen yang tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDetail(
        nama: String?,
        kodeTender: String?,
        jenisKualifikasi: String?,
        keterangan: String?,
        mulai: String?,
        selesai: String?
    ) {
        binding.namaTender.text = nama
        binding.textViewKodeRUP.text = getString(R.string.kode_tender_data) + " $kodeTender"
        binding.textViewKualifikasi.text = getString(R.string.jenis_kualifikasi) + " $jenisKualifikasi"
        binding.textViewKeterangan.text = getString(R.string.keterangan_kualifikasi) + " $keterangan"
        binding.textViewMulai.text = getString(R.string.tanggal_mulai) + " $mulai"
        binding.textViewSelesai.text = getString(R.string.tanggal_selesai) + " $selesai"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {

            val updateNama = data?.getStringExtra("updateNama")
            val updateKodeTender = data?.getStringExtra("updateKodeTender")
            val updateJenisKualifikasi = data?.getStringExtra("updateJenisKualifikasi")
            val updateKeterangan = data?.getStringExtra("updateKeterangan")
            val updateMulai = data?.getStringExtra("updateMulai")
            val updateSelesai = data?.getStringExtra("updateSelesai")
            val updatedDokumen = data?.getStringExtra("updatedDokumenTender")

            binding.namaTender.text = updateNama
            binding.textViewKodeRUP.text = getString(R.string.kode_tender_data) + " $updateKodeTender"
            binding.textViewKualifikasi.text = getString(R.string.jenis_kualifikasi) + " $updateJenisKualifikasi"
            binding.textViewKeterangan.text = getString(R.string.keterangan_kualifikasi) + " $updateKeterangan"
            binding.textViewMulai.text = getString(R.string.tanggal_mulai) + " $updateMulai"
            binding.textViewSelesai.text = getString(R.string.tanggal_selesai) + " $updateSelesai"
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