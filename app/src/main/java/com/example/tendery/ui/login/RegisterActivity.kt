package com.example.tendery.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.tendery.MainActivity
import com.example.tendery.R
import com.example.tendery.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private var selectedRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        val roleOptions = arrayOf("PPK", "Pemberi Jasa", "Penyedia")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roleOptions)
        val autoCompleteTextView = binding.roleSpinner.editText as AutoCompleteTextView

        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.dropDownAnchor = binding.roleSpinner.id
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.dropDownAnchor = binding.roleSpinner.id

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            selectedRole = roleOptions[position]
            Log.d("bayauu",selectedRole)
        }



        binding.daftarButton.setOnClickListener {
            signUp()
        }
    }


    private fun signUp() {
        val name = binding.namaEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val role =selectedRole
        val PreTest = 0
        val PostTest = 0
        if (checkForm()) {
            binding.progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        val userInfo = hashMapOf(
                            "FullName" to name,
                            "UserEmail" to email,
                            "PreTest" to PreTest,
                            "PostTest" to PostTest
                        )
                        when (role) {
                            "PPK" -> {
                                userInfo["Role"] = "PPK"
                            }
                            "Pemberi Jasa" -> {
                                userInfo["Role"] = "Pemberi Jasa"
                            }
                            "Penyedia" -> {
                                userInfo["Role"] = "Penyedia"
                            }
                        }

                        val df = fStore.collection("Users").document(currentUser?.uid.toString())
                        df.set(userInfo)

                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(baseContext, "Registrasi Akun Berhasil!", Toast.LENGTH_SHORT,).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(baseContext, "Gagal membuat akun, silahkan coba lagi", Toast.LENGTH_SHORT,).show()
                    }
                }

        }
    }

    private fun checkForm(): Boolean {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val name = binding.namaEditText.text.toString()
        var isValid = true

        when {
            name.isEmpty() -> {
                binding.namaEditTextLayout.error = getString(R.string.error_nama)
                isValid = false
            }

            else -> {
                binding.namaEditTextLayout.error = null
            }
        }

        when {
            email.isEmpty() -> {
                binding.emailEditTextLayout.error = getString(R.string.error_enter_email)
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.emailEditTextLayout.error = getString(R.string.invalid_email)
                isValid = false
            }
            else -> {
                binding.emailEditTextLayout.error = null
            }
        }

        when {
            password.isEmpty() -> {
                binding.passwordEditTextLayout.error = getString(R.string.error_passrword)
                isValid = false
            }
            password.length < 8 -> {
                binding.passwordEditTextLayout.error = getString(R.string.password_leght)
                isValid = false
            }
            else -> {
                binding.passwordEditTextLayout.error = null
            }
        }

        val selectedRole = binding.roleSpinner.editText?.text.toString()
        when {
            selectedRole.isEmpty() -> {
                binding.roleSpinner.error = getString(R.string.error_select_role)
                isValid = false
            }
            else -> {
                binding.roleSpinner.error = null
            }
        }

        return isValid
    }
}