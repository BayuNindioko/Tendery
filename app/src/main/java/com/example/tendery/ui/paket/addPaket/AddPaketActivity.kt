package com.example.tendery.ui.paket.addPaket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityAddPaketBinding
import com.example.tendery.databinding.ActivityAddUserBinding
import com.example.tendery.databinding.ActivityListUserBinding
import com.example.tendery.ui.admin.addUser.AddUserActivity
import com.example.tendery.ui.admin.editUser.EditAkunActivity

class AddPaketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Paket Tender"
        }
        binding = ActivityAddPaketBinding.inflate(layoutInflater)
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