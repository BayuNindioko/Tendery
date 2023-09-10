package com.example.tendery.ui.postTest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPertanyaanBinding
import com.example.tendery.databinding.FragmentPostTestBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.pertanyaan.detailPertanyaan.DetailPertanyaanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class PostTestFragment : Fragment() {

    private var _binding: FragmentPostTestBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostTestBinding.inflate(inflater, container, false)

        val root: View = binding.root

        getData()
        binding.button.setOnClickListener {
            val intent = Intent(requireContext(), PostTestActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun getData() {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser
        binding.progressBar3.visibility = View.VISIBLE
        if (currentUser != null) {
            val uid = currentUser.uid

            fStore.collection("Users").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->

                    if (documentSnapshot.contains("PreTest")) {
                        val preTestValue = documentSnapshot.get("PreTest")
                        if (preTestValue is Long) {
                            if (preTestValue < 50) {
                                binding.hasilPreTest.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                            } else {
                                binding.hasilPreTest.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            }
                            binding.hasilPreTest.text = preTestValue.toString()
                            binding.progressBar3.visibility = View.GONE
                        } else {
                            binding.hasilPreTest.text = "Data nilai tidak valid"
                            binding.progressBar3.visibility = View.GONE
                        }
                    } else {
                        binding.hasilPreTest.text = "Belum ada nilai Post Test"
                        binding.progressBar3.visibility = View.GONE
                    }

                    //PostTest
                    if (documentSnapshot.contains("PostTest")) {
                        val postTestValue = documentSnapshot.get("PostTest")
                        if (postTestValue is Long) {
                            if (postTestValue < 50) {
                                binding.hasilPostTest.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                            } else {
                                binding.hasilPostTest.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                            }
                            binding.hasilPostTest.text = postTestValue.toString()
                            binding.progressBar3.visibility = View.GONE
                        } else {
                            binding.hasilPostTest.text = "Data nilai tidak valid"
                            binding.progressBar3.visibility = View.GONE
                        }
                    } else {
                        binding.hasilPostTest.text = "Belum ada nilai Post Test"
                        binding.progressBar3.visibility = View.GONE
                    }
                }
                .addOnFailureListener { e ->
                    binding.hasilPostTest.text = "Gagal mendapatkan nilai: ${e.message}"
                    binding.progressBar3.visibility = View.GONE
                }
        } else {
            binding.hasilPostTest.text = "Anda belum masuk atau ada masalah dengan akun Anda."
            binding.progressBar3.visibility = View.GONE
        }
    }
}