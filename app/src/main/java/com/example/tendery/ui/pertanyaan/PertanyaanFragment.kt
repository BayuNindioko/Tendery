package com.example.tendery.ui.pertanyaan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPenawaranBinding
import com.example.tendery.databinding.FragmentPertanyaanBinding
import com.example.tendery.ui.penawaran.addPenawaran.AddPenawaranActivity
import com.example.tendery.ui.penawaran.detailPenawaran.DetailPenawaranActivity
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.pertanyaan.detailPertanyaan.DetailPertanyaanActivity


class PertanyaanFragment : Fragment() {


    private var _binding: FragmentPertanyaanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPertanyaanBinding.inflate(inflater, container, false)

        val root: View = binding.root
        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), DetailPertanyaanActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton4.setOnClickListener {
            val intent = Intent(requireContext(), AddPertanyaanActivity::class.java)
            startActivity(intent)
        }

        return root
        }
    }