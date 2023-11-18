package com.example.tendery.ui.pertanyaan.jawaban

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.api.ApiConfig
import com.example.tendery.api.ApiService
import com.example.tendery.databinding.ActivityJawabanBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddJawabanActivity
import com.example.tendery.api.RequestBody
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JawabanActivity : AppCompatActivity(), JawabanAdapter.OnItemClickListener {
    private lateinit var binding: ActivityJawabanBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference

    private lateinit var apiService: ApiService
    private lateinit var adapter: JawabanAdapter

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

        apiService = ApiConfig().getApiService()
        setupRecyclerView()
        loadDataFromApi()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        adapter = JawabanAdapter(this)
        binding.rvJawaban.adapter = adapter
        binding.rvJawaban.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(kode: String, pertanyaan: String) {

        val intent = Intent(this, DetailJawabanActivity::class.java)
        intent.putExtra("KODE_PERTANYAAN", kode)
        intent.putExtra("PERTANYAAN", pertanyaan)
        startActivity(intent)
    }
    private fun loadDataFromApi() {
        val request = RequestBody(
            method = "getAllAnswer",
            params = listOf("privatekey")
        )

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getAnswer(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        runOnUiThread {
                            adapter.setData(it.message)
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@JawabanActivity, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@JawabanActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}