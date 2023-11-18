package com.example.tendery.ui.pertanyaan.addPertanyaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.tendery.api.ApiConfig
import com.example.tendery.api.RequestBody
import com.example.tendery.databinding.ActivityAddJawabanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddJawabanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddJawabanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Jawaban"
        }
        binding = ActivityAddJawabanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val kodeTender = binding.kodeEditText.text.toString()
        val jawaban = binding.jawabanEditText.text.toString()


        if (kodeTender.isEmpty()|| jawaban.isEmpty() ) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        } else {
            val apiService = ApiConfig().getApiService()

            val request = RequestBody(
                method = "createAnswer",
                params = listOf("privatekey", kodeTender, jawaban)
            )

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.createAnswer(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        runOnUiThread {
                            Toast.makeText(this@AddJawabanActivity, "Jawaban berhasil ditambah!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AddJawabanActivity, "Gagal melakukan permintaan", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@AddJawabanActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
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