package com.example.tendery.ui.paket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.paket.rv.PaketAdapter

import com.example.tendery.ui.paket.rv.PaketModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class PaketFragment : Fragment() {

    private var _binding: FragmentPaketBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaketBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val paketViewModel =
            ViewModelProvider(this).get(PaketViewModel::class.java)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia" || role == "Pemberi Jasa") {
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


        binding.rvPaket.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPaket.setHasFixedSize(true)
        binding.progressBar4.visibility = View.VISIBLE
        getData()

        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(requireContext(), AddPaketActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {

        dbRef = FirebaseDatabase.getInstance().getReference("Paket_Tender")
        val paketAdapter = PaketAdapter(ArrayList())
        binding.rvPaket.adapter = paketAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val paketList = ArrayList<PaketModel>()
                for (paketSnap in snapshot.children) {
                    val paketData = paketSnap.getValue(PaketModel::class.java)
                    paketData?.let { paketList.add(it) }
                }
                paketAdapter.updateData(paketList)
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })

        paketAdapter.setOnItemClickListener(object : PaketAdapter.OnItemClickListener {
            override fun onItemClick(paketModel: PaketModel) {
                val intent = Intent(requireContext(), DetailPaketActivity::class.java)

                // Mengirim data yang dibutuhkan melalui Intent
                intent.putExtra("paketid", paketModel.id)
                intent.putExtra("paketNama", paketModel.nama)
                intent.putExtra("paketKodeRup", paketModel.kodeRup)
                intent.putExtra("paketKlpd", paketModel.klpd)
                intent.putExtra("paketNilaiPagu", paketModel.nilaiPagu)
                intent.putExtra("paketTahun", paketModel.tahun)
                intent.putExtra("paketJenisPengadaan", paketModel.jenisPengadaan)
                intent.putExtra("paketProvinsi", paketModel.provinsi)
                intent.putExtra("paketKabupatenKota", paketModel.kabupatenKota)
                intent.putExtra("paketLokasiLengkap", paketModel.lokasiLengkap)

                startActivity(intent)
            }
        })

    }
}
