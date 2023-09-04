package com.example.tendery.ui.penawaran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.databinding.FragmentPenawaranBinding
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.penawaran.addPenawaran.AddPenawaranActivity
import com.example.tendery.ui.penawaran.detailPenawaran.DetailPenawaranActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class PenawaranFragment : Fragment() {
    private var _binding: FragmentPenawaranBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenawaranBinding.inflate(inflater, container, false)

        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "PPK" ||role == "Pemberi Jasa") {
                        binding.floatingActionButton4.visibility = View.GONE
                    }else {
                        binding.floatingActionButton4.visibility = View.VISIBLE
                    }
                } else {
                    binding.floatingActionButton4.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }

        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), DetailPenawaranActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(requireContext(), AddPenawaranActivity::class.java)
            startActivity(intent)
        }

        return root
    }


}