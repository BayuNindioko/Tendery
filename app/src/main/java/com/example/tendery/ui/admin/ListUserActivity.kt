package com.example.tendery.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.tendery.R
import com.example.tendery.databinding.ActivityListUserBinding
import com.example.tendery.databinding.ActivityMainBinding
import com.example.tendery.ui.admin.addUser.AddUserActivity
import com.example.tendery.ui.admin.editUser.EditAkunActivity
import com.google.android.material.snackbar.Snackbar

class ListUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.daftar_pengguna)
        }
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardUser.setOnClickListener{
            val intent = Intent(this, EditAkunActivity::class.java)
            startActivity(intent)
        }
        binding.floatingActionButton2.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
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