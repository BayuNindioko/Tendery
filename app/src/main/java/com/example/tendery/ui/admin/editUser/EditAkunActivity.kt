package com.example.tendery.ui.admin.editUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddUserBinding
import com.example.tendery.databinding.ActivityEditAkunBinding

class EditAkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title ="Edit User"
        }
        binding = ActivityEditAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}