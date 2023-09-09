package com.example.tendery.ui.hps.detailHPS

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.tendery.R
import com.example.tendery.databinding.ActivityRincianHpsBinding
import com.example.tendery.ui.hps.editHPS.EdithpsActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Rincian_hpsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRincianHpsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    companion object {
        const val EDIT_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Rincian HPS"
        }
        binding = ActivityRincianHpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia" ||role == "Pemberi Jasa") {
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
                Toast.makeText(this, "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }
        val id = intent.getStringExtra("HpsId")
        val kodeRup = intent.getStringExtra("kodeRup")
        val jenisBarangJasa = intent.getStringExtra("jenisBarangJasa")
        val satuan = intent.getStringExtra("satuan")
        val harga = intent.getStringExtra("harga")
        val pajak = intent.getStringExtra("pajak")
        val total = intent.getStringExtra("total")
        val keterangan = intent.getStringExtra("keterangan")
        val nilaiPagu = intent.getStringExtra("nilaiPagu")
        val dokumenPersiapan = intent.getStringExtra("dokumenPersiapan")

        getDetail(kodeRup,jenisBarangJasa,satuan,harga,pajak,total,keterangan,nilaiPagu,dokumenPersiapan)

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EdithpsActivity::class.java)
            intent.putExtra("HpsId", id)
            intent.putExtra("kodeRup", kodeRup)
            intent.putExtra("jenisBarangJasa", jenisBarangJasa)
            intent.putExtra("satuan", satuan)
            intent.putExtra("harga", harga)
            intent.putExtra("pajak", pajak)
            intent.putExtra("total", total)
            intent.putExtra("keterangan", keterangan)
            intent.putExtra("nilaiPagu", nilaiPagu)
            intent.putExtra("dokumenPersiapan", dokumenPersiapan)

            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        binding.fabDelete.setOnClickListener {
            binding.progressBar4.visibility = View.VISIBLE
            val dbRef = FirebaseDatabase.getInstance().getReference("HPS").child(id.toString())
            val mTask = dbRef.removeValue()

            mTask.addOnSuccessListener {
                binding.progressBar4.visibility = View.GONE
                Toast.makeText(this, "Data Berhasil Dihapus!", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener{ error ->
                Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDetail(
        kodeRup: String?,
        jenisBarangJasa: String?,
        satuan: String?,
        harga: String?,
        pajak: String?,
        total: String?,
        keterangan: String?,
        nilaiPagu: String?,
        dokumenPersiapan: String?
    ) {
        binding.textViewKodeRUP.text = getString(R.string.kode_rup_label) + " " + kodeRup
        binding.textViewJenisBarang.text = getString(R.string.jenis_barang_label) + " " + jenisBarangJasa
        binding.textViewSatuan.text = getString(R.string.satuan_label) + " " + satuan
        binding.textViewHargaPajak.text = getString(R.string.harga_label) + " " + harga
        binding.textViewPajak.text = getString(R.string.pajak_label) + " " + pajak
        binding.textViewTotal.text = getString(R.string.total_label) + " " + total
        binding.textViewKeterangan.text = getString(R.string.keterangan_label) + " " + keterangan
        binding.textViewNilaiPagu.text = getString(R.string.nilai_pagu_label) + " " + nilaiPagu

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            val updatedKodeRup = data?.getStringExtra("updatedKodeRup")
            val updatedJenisBarangJasa = data?.getStringExtra("updatedJenisBarangJasa")
            val updatedSatuan = data?.getStringExtra("updatedSatuan")
            val updatedHarga = data?.getStringExtra("updatedHarga")
            val updatedPajak = data?.getStringExtra("updatedPajak")
            val updatedTotal = data?.getStringExtra("updatedTotal")
            val updatedKeterangan = data?.getStringExtra("updatedKeterangan")
            val updatedNilaiPagu = data?.getStringExtra("updatedNilaiPagu")
            val updatedDokumen = data?.getStringExtra("updatedDokumen")

            binding.textViewKodeRUP.text = getString(R.string.kode_rup_label) + " " + updatedKodeRup
            binding.textViewJenisBarang.text = getString(R.string.jenis_barang_label) + " " + updatedJenisBarangJasa
            binding.textViewSatuan.text = getString(R.string.satuan_label) + " " + updatedSatuan
            binding.textViewHargaPajak.text = getString(R.string.harga_label) + " " + updatedHarga
            binding.textViewPajak.text = getString(R.string.pajak_label) + " " + updatedPajak
            binding.textViewTotal.text = getString(R.string.total_label) + " " + updatedTotal
            binding.textViewKeterangan.text = getString(R.string.keterangan_label) + " " + updatedKeterangan
            binding.textViewNilaiPagu.text = getString(R.string.nilai_pagu_label) + " " + updatedNilaiPagu
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