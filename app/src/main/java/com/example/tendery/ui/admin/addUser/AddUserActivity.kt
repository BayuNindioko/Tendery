package com.example.tendery.ui.admin.addUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddUserBinding
import com.example.tendery.databinding.ActivityListUserBinding

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Tambah User"
        }
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(this, R.layout.spinner_list, items)

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoComplete)
        autoCompleteTextView.setAdapter(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}