package com.example.tendery.ui.data_tender

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tendery.databinding.FragmentDataTenderBinding
import com.example.tendery.ui.data_tender.addTender.AddTenderActivity
import com.example.tendery.ui.data_tender.detailTender.DetailTenderActivity
import com.example.tendery.ui.data_tender.rv.DataModel
import com.example.tendery.ui.data_tender.rv.DataTenderAdapter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class DataTenderFragment : Fragment() {
    private var _binding: FragmentDataTenderBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataTenderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        val currentUser = auth.currentUser

        val userDocRef = fStore.collection("Users").document(currentUser?.uid.toString())

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val role = document.getString("Role")
                    if (role == "Penyedia"|| role =="PPK") {
                        binding.floatingActionButton3.visibility = View.GONE
                    }else{
                        binding.floatingActionButton3.visibility = View.VISIBLE
                    }
                } else {
                    binding.floatingActionButton3.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Coba Lagi", Toast.LENGTH_SHORT,).show()
            }

        binding.rvData.layoutManager = LinearLayoutManager(requireContext())
        binding.rvData.setHasFixedSize(true)
        binding.progressBar6.visibility = View.VISIBLE
        getData()

        binding.floatingActionButton3.setOnClickListener {
            val intent = Intent(requireContext(), AddTenderActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Data_Tender")
        val dataTenderAdapter =DataTenderAdapter(ArrayList())
        binding.rvData.adapter = dataTenderAdapter

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataTenderList = ArrayList<DataModel>()
                for (dataSnap in snapshot.children) {
                    val dataTenderData = dataSnap.getValue(DataModel::class.java)
                    dataTenderData?.let { dataTenderList.add(it) }
                }
                dataTenderAdapter.updateData(dataTenderList)
                binding.progressBar6.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })

        dataTenderAdapter.setOnItemClickListener(object : DataTenderAdapter.OnItemClickListener {
            override fun onItemClick(dataModel: DataModel) {
                val intent = Intent(requireContext(), DetailTenderActivity::class.java)

                intent.putExtra("DataId", dataModel.id)
                intent.putExtra("nama", dataModel.nama)
                intent.putExtra("kodeTender", dataModel.kodeTender)
                intent.putExtra("jenisKualifikasi", dataModel.jenisKualifikasi)
                intent.putExtra("keterangan", dataModel.keternagan)
                intent.putExtra("mulai", dataModel.mulai)
                intent.putExtra("selesai", dataModel.selesai)
                intent.putExtra("dokumenTender", dataModel.dokumenTender)

                startActivity(intent)
            }
        })
    }

}