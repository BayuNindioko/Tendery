package com.example.tendery.ui.penawaran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.databinding.FragmentPenawaranBinding

import com.example.tendery.ui.penawaran.addPenawaran.AddPenawaranActivity
import com.example.tendery.ui.penawaran.detailPenawaran.DetailPenawaranActivity
import com.example.tendery.ui.penawaran.rv.PenawaranAdapter
import com.example.tendery.ui.penawaran.rv.PenawaranModel
import com.example.tendery.ui.pertanyaan.detailPertanyaan.DetailPertanyaanActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class PenawaranFragment : Fragment() {
    private var _binding: FragmentPenawaranBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference
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

        binding.rvPenawaran.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPenawaran.setHasFixedSize(true)
        binding.progressBar4.visibility = View.VISIBLE
        getData()

        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(requireContext(), AddPenawaranActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Penawaran")
        val penawaranAdapter = PenawaranAdapter(ArrayList())
        binding.rvPenawaran.adapter = penawaranAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val penawaranList = ArrayList<PenawaranModel>()
                for (penawaranSnap in snapshot.children) {
                    val penawaranData = penawaranSnap.getValue(PenawaranModel::class.java)
                    penawaranData?.let { penawaranList.add(it) }
                }
                penawaranAdapter.updateData(penawaranList)
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })

        penawaranAdapter.setOnItemClickListener(object : PenawaranAdapter.OnItemClickListener {
            override fun onItemClick(penawaranModel: PenawaranModel) {
                val intent = Intent(requireContext(), DetailPenawaranActivity::class.java)

                intent.putExtra("penawaranId", penawaranModel.id)
                intent.putExtra("kodePenawaran", penawaranModel.kodePenawaran)
                intent.putExtra("hargaPenawaran", penawaranModel.hargaPenawaran)
                intent.putExtra("dokumenPenawaran", penawaranModel.dokumenPenawaran)

                startActivity(intent)
            }
        })
    }


}