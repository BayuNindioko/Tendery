package com.example.tendery.ui.paket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.ui.admin.addUser.AddUserActivity
import com.example.tendery.ui.admin.editUser.EditAkunActivity
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.paket.editPaket.EditPaketActivity

class PaketFragment : Fragment() {

    private var _binding: FragmentPaketBinding? = null
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
