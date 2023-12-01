package com.example.tendery.ui.admin.mainAdmin.ui.pertanyaan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.databinding.FragmentDashboardBinding
import com.example.tendery.ui.preTest.Soal
import com.google.firebase.firestore.FirebaseFirestore

class PertanyaanAdminFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = FirebaseFirestore.getInstance()
        val listSoalJawaban = ArrayList<Soal>()

        val docRef = db.collection("Test").document("nrctuyVsbfg2xZleMTJA")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val listSoal = document["list_soal"] as Map<String, Any>

                    for (i in 1..10) {
                        val soalData = listSoal["soal$i"] as Map<String, Any>
                        val soal = soalData["soal"] as String
                        val pilihanA = soalData["A"] as String
                        val pilihanB = soalData["B"] as String
                        val pilihanC = soalData["C"] as String
                        val jawaban = soalData["jawaban"] as String

                        val soalJawaban = Soal(soal, pilihanA, pilihanB, pilihanC, jawaban)
                        listSoalJawaban.add(soalJawaban)
                    }


                    val recyclerView = binding.rvSoal
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    val adapter = SoalAdminAdapter(listSoalJawaban) { soal->
                        val i = listSoalJawaban.indexOf(soal) + 1
                        val intent = Intent(requireContext(), EditSoalActivity::class.java)
                        intent.putExtra("soal", soal.soal)
                        intent.putExtra("jawaban", soal.jawaban)
                        intent.putExtra("pilihanA", soal.A)
                        intent.putExtra("pilihanB", soal.B)
                        intent.putExtra("pilihanC", soal.C)
                        intent.putExtra("nomorSoal", i)
                        startActivity(intent)
                    }

                    recyclerView.adapter = adapter

                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Dokumen tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Dokumen tidak ditemukan", Toast.LENGTH_SHORT).show()
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}