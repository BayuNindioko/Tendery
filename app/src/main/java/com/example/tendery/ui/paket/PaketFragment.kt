package com.example.tendery.ui.paket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.ui.admin.addUser.AddUserActivity
import com.example.tendery.ui.admin.editUser.EditAkunActivity
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.paket.editPaket.EditPaketActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PaketFragment : Fragment() {

    private var _binding: FragmentPaketBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
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

        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), DetailPaketActivity::class.java)
            startActivity(intent)
        }

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


}
