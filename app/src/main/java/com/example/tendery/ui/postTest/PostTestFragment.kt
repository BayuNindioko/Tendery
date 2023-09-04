package com.example.tendery.ui.postTest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.tendery.R
import com.example.tendery.databinding.FragmentPertanyaanBinding
import com.example.tendery.databinding.FragmentPostTestBinding
import com.example.tendery.ui.pertanyaan.addPertanyaan.AddPertanyaanActivity
import com.example.tendery.ui.pertanyaan.detailPertanyaan.DetailPertanyaanActivity


class PostTestFragment : Fragment() {


    private var _binding: FragmentPostTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostTestBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.button.setOnClickListener {
            val intent = Intent(requireContext(), PostTestActivity::class.java)
            startActivity(intent)
        }

        return root
    }


}