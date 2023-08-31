package com.example.tendery.ui.penawaran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPaketBinding
import com.example.tendery.databinding.FragmentPenawaranBinding
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity
import com.example.tendery.ui.penawaran.addPenawaran.AddPenawaranActivity
import com.example.tendery.ui.penawaran.detailPenawaran.DetailPenawaranActivity


class PenawaranFragment : Fragment() {
    private var _binding: FragmentPenawaranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenawaranBinding.inflate(inflater, container, false)

        val root: View = binding.root
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