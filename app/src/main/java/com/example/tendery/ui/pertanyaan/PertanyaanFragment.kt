package com.example.tendery.ui.pertanyaan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPenawaranBinding
import com.example.tendery.databinding.FragmentPertanyaanBinding
import com.example.tendery.ui.penawaran.addPenawaran.AddPenawaranActivity
import com.example.tendery.ui.penawaran.detailPenawaran.DetailPenawaranActivity
import com.example.tendery.ui.penawaran.rv.PenawaranAdapter
import com.example.tendery.ui.penawaran.rv.PenawaranModel
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.pertanyaan.detailPertanyaan.DetailPertanyaanActivity
import com.example.tendery.ui.pertanyaan.jawaban.JawabanActivity
import com.example.tendery.ui.pertanyaan.rv.PertanyaanAdapter
import com.example.tendery.ui.pertanyaan.rv.PertanyaanModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class PertanyaanFragment : Fragment() {
    private var _binding: FragmentPertanyaanBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPertanyaanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "PPK" || role == "Pemberi Jasa") {
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

        binding.rvPertanyaan.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPertanyaan.setHasFixedSize(true)
        binding.progressBar4.visibility = View.VISIBLE
        getData()
        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(requireContext(), AddPertanyaanActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val intent = Intent(requireContext(), JawabanActivity::class.java)
            startActivity(intent)
        }

        return root
        }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Pertanyaan")
        val pertanyaanAdapter = PertanyaanAdapter(ArrayList())
        binding.rvPertanyaan.adapter = pertanyaanAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pertanyaanList = ArrayList<PertanyaanModel>()
                for (pertanyaanSnap in snapshot.children) {
                    val penawaranData = pertanyaanSnap.getValue(PertanyaanModel::class.java)
                    penawaranData?.let { pertanyaanList.add(it) }
                }
                pertanyaanAdapter.updateData(pertanyaanList)
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })

        pertanyaanAdapter.setOnItemClickListener(object : PertanyaanAdapter.OnItemClickListener {
            override fun onItemClick(pertanyaanModel: PertanyaanModel) {
                val intent = Intent(requireContext(), DetailPertanyaanActivity::class.java)

                intent.putExtra("pertanyaanId", pertanyaanModel.id)
                intent.putExtra("kodeTender", pertanyaanModel.kodeTender)
                intent.putExtra("kodePertanyaan", pertanyaanModel.kodePertanyaan)
                intent.putExtra("pertanyaan", pertanyaanModel.pertanyaan)

                startActivity(intent)
            }
        })
    }
}