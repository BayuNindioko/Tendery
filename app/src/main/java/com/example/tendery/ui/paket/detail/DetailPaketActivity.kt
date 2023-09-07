package com.example.tendery.ui.paket.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityDetailPaketBinding
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.ui.data_tender.editDataTender.EditDataTenderActivity
import com.example.tendery.ui.paket.PaketFragment
import com.example.tendery.ui.paket.editPaket.EditPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        val id = intent.getStringExtra("paketid")

        binding.fabDelete.setOnClickListener {
            binding.progressBar4.visibility = View.VISIBLE
            val dbRef = FirebaseDatabase.getInstance().getReference("Paket_Tender").child(id.toString())
            val mTask = dbRef.removeValue()

            mTask.addOnSuccessListener {
                binding.progressBar4.visibility = View.GONE
                Toast.makeText(this, "Data Berhasil Dihapus!", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
            }
        }

        val nama = intent.getStringExtra("paketNama")
        val kodeRup = intent.getStringExtra("paketKodeRup")
        val klpd = intent.getStringExtra("paketKlpd")
        val nilaiPagu = intent.getStringExtra("paketNilaiPagu")
        val tahun = intent.getStringExtra("paketTahun")
        val jenisPengadaan = intent.getStringExtra("paketJenisPengadaan")
        val provinsi = intent.getStringExtra("paketProvinsi")
        val kabupatenKota = intent.getStringExtra("paketKabupatenKota")
        val lokasiLengkap = intent.getStringExtra("paketLokasiLengkap")

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditPaketActivity::class.java)

            intent.putExtra("paketid", id)
            intent.putExtra("paketNama", nama)
            intent.putExtra("paketKodeRup", kodeRup)
            intent.putExtra("paketKlpd", klpd)
            intent.putExtra("paketNilaiPagu", nilaiPagu)
            intent.putExtra("paketTahun", tahun)
            intent.putExtra("paketJenisPengadaan", jenisPengadaan)
            intent.putExtra("paketProvinsi", provinsi)
            intent.putExtra("paketKabupatenKota", kabupatenKota)
            intent.putExtra("paketLokasiLengkap", lokasiLengkap)
            startActivity(intent)
        }

        getDetail(nama,kodeRup,klpd,nilaiPagu,tahun,jenisPengadaan,provinsi,kabupatenKota,lokasiLengkap)
    }

    private fun getDetail(nama:String?,kodeRup:String?,klpd:String?,nilaiPagu:String?,tahun:String?,jenisPengadaan:String?,provinsi:String?,kabupatenKota:String?,lokasiLengkap:String?) {

        binding.textViewNamaPaket.text = nama
        binding.textViewKodeRUP.text = getString(R.string.kode_rup) + " " + kodeRup
        binding.textViewKLPD.text = getString(R.string.k_l_pd) + " " + klpd
        binding.textViewNilaiPagu.text = getString(R.string.nilai_pagu) + " " + nilaiPagu
        binding.textViewTahun.text = getString(R.string.tahun) + " " + tahun
        binding.textViewJenisPengadaan.text = getString(R.string.jenis) + " " + jenisPengadaan
        binding.textViewProvinsi.text = getString(R.string.provinsi) + " " + provinsi
        binding.textViewKabupatenKota.text = getString(R.string.kabupaten_kota) + " " + kabupatenKota
        binding.textViewLokasiLengkap.text = getString(R.string.lokasi_lengkap) + " " + lokasiLengkap
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}