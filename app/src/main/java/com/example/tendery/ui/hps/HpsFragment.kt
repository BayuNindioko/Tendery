package com.example.tendery.ui.hps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tendery.R
import com.example.tendery.databinding.FragmentHpsBinding
import com.example.tendery.ui.hps.addHPS.AddHPSActivity
import com.example.tendery.ui.hps.detailHPS.Rincian_hpsActivity
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HpsFragment : Fragment() {

    private var _binding: FragmentHpsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val hpsViewModel =
            ViewModelProvider(this).get(HpsViewModel::class.java)

        _binding = FragmentHpsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")

                    when (role) {
                        "Penyedia" -> {
                            binding.needAcess.visibility = View.VISIBLE
                            binding.textSlideshow.visibility = View.GONE
                            binding.cardUser.visibility = View.GONE
                            binding.floatingActionButton3.visibility = View.GONE
                        }
                        "Pemberi Jasa" -> {
                            binding.needAcess.visibility = View.GONE
                            binding.textSlideshow.visibility = View.VISIBLE
                            binding.cardUser.visibility = View.VISIBLE
                            binding.floatingActionButton3.visibility = View.GONE
                        }
                        else -> {
                            binding.needAcess.visibility = View.GONE
                            binding.textSlideshow.visibility = View.VISIBLE
                            binding.cardUser.visibility = View.VISIBLE
                            binding.floatingActionButton3.visibility = View.VISIBLE
                        }
                    }

                } else {
                    binding.needAcess.visibility = View.GONE
                    binding.textSlideshow.visibility = View.VISIBLE
                    binding.cardUser.visibility = View.VISIBLE
                    binding.floatingActionButton3.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }

        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), Rincian_hpsActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton3.setOnClickListener {
            val intent = Intent(requireContext(), AddHPSActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}