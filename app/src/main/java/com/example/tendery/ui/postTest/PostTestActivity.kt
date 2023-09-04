package com.example.tendery.ui.postTest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityEditJawabanBinding
import com.example.tendery.databinding.ActivityPostTestBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.postTest.nilai.NilaiPostTestActivity

class PostTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Post Test"
        }
        binding = ActivityPostTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, NilaiPostTestActivity::class.java)
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