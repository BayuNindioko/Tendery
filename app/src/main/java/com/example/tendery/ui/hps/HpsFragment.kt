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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.R
import com.example.tendery.databinding.FragmentHpsBinding
import com.example.tendery.ui.hps.addHPS.AddHPSActivity
import com.example.tendery.ui.hps.detailHPS.Rincian_hpsActivity
import com.example.tendery.ui.hps.rv.HpsAdapter
import com.example.tendery.ui.hps.rv.HpsModel
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

class HpsFragment : Fragment() {

    private var _binding: FragmentHpsBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference
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
                            binding.rvHps.visibility = View.GONE
                            binding.floatingActionButton3.visibility = View.GONE
                        }
                        "Pemberi Jasa" -> {
                            binding.needAcess.visibility = View.GONE
                            binding.textSlideshow.visibility = View.VISIBLE
                            binding.rvHps.visibility = View.VISIBLE
                            binding.floatingActionButton3.visibility = View.GONE
                        }
                        else -> {
                            binding.needAcess.visibility = View.GONE
                            binding.textSlideshow.visibility = View.VISIBLE
                            binding.rvHps.visibility = View.VISIBLE
                            binding.floatingActionButton3.visibility = View.VISIBLE
                        }
                    }

                } else {
                    binding.needAcess.visibility = View.GONE
                    binding.textSlideshow.visibility = View.VISIBLE
                    binding.rvHps.visibility = View.VISIBLE
                    binding.floatingActionButton3.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }

        binding.rvHps.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHps.setHasFixedSize(true)
        binding.progressBar5.visibility = View.VISIBLE
        getData()

        binding.floatingActionButton3.setOnClickListener {
            val intent = Intent(requireContext(), AddHPSActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("HPS")
        val hpsAdapter = HpsAdapter(ArrayList())
        binding.rvHps.adapter = hpsAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val hpsList = ArrayList<HpsModel>()
                for (hpsSnap in snapshot.children) {
                    val hpsData = hpsSnap.getValue(HpsModel::class.java)
                    hpsData?.let { hpsList.add(it) }
                }
                hpsAdapter.updateData(hpsList)
                binding.progressBar5.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })

        hpsAdapter.setOnItemClickListener(object : HpsAdapter.OnItemClickListener {
            override fun onItemClick(hpsModel: HpsModel) {
                val intent = Intent(requireContext(), Rincian_hpsActivity::class.java)

                intent.putExtra("HpsId", hpsModel.id)
                intent.putExtra("kodeRup", hpsModel.kodeRup)
                intent.putExtra("jenisBarangJasa", hpsModel.jenisBarangJasa)
                intent.putExtra("satuan", hpsModel.satuan)
                intent.putExtra("harga", hpsModel.harga)
                intent.putExtra("pajak", hpsModel.pajak)
                intent.putExtra("total", hpsModel.total)
                intent.putExtra("keterangan", hpsModel.keterangan)
                intent.putExtra("nilaiPagu", hpsModel.nilaiPagu)
                intent.putExtra("dokumenPersiapan", hpsModel.dokumenPersiapan)


                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}