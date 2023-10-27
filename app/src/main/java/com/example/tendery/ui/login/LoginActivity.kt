package com.example.tendery.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.tendery.MainActivity
import com.example.tendery.R
import com.example.tendery.databinding.ActivityLoginBinding
import com.example.tendery.ui.admin.addUser.AddUserActivity
import com.example.tendery.ui.admin.mainAdmin.AdminMainActivity
import com.example.tendery.ui.preTest.PreTestActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()


        binding.loginButton.setOnClickListener {
            signIn()
        }

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (checkForm()) {
            binding.progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(baseContext, "Selamat Datang di Tendery!", Toast.LENGTH_SHORT).show()


                        val currentUser = auth.currentUser
                        val uid = currentUser?.uid

                        if (uid != null) {
                            fStore.collection("Users").document(uid)
                                .get()
                                .addOnSuccessListener { documentSnapshot ->
                                    if (documentSnapshot.contains("Role")) {
                                        val role = documentSnapshot.getString("Role")
                                        if (role == "Admin") {
                                            val adminIntent = Intent(this, AdminMainActivity::class.java)
                                            startActivity(adminIntent)
                                            finish()
                                        } else if (documentSnapshot.contains("PreTest")) {
                                            val preTestValue = documentSnapshot.getLong("PreTest")
                                            if (preTestValue != null) {
                                                if (preTestValue == 0L) {
                                                    val intent = Intent(this, PreTestActivity::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                } else {
                                                    val intent = Intent(this, MainActivity::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                            } else {
                                                Toast.makeText(baseContext, "Data nilai PreTest tidak valid", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Toast.makeText(baseContext, "Belum ada nilai PreTest", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(baseContext, "Role tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(baseContext, "Gagal mendapatkan data pengguna: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(baseContext, "Login Gagal, coba lagi!", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun checkForm(): Boolean {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        var isValid = true

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

        return isValid
    }

}