package com.example.tendery.ui.data_tender

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tendery.R
import com.example.tendery.databinding.FragmentDataTenderBinding
import com.example.tendery.databinding.FragmentHpsBinding
import com.example.tendery.ui.data_tender.addTender.AddTenderActivity
import com.example.tendery.ui.data_tender.detailTender.DetailTenderActivity
import com.example.tendery.ui.hps.addHPS.AddHPSActivity
import com.example.tendery.ui.hps.detailHPS.Rincian_hpsActivity

class DataTenderFragment : Fragment() {
    private var _binding: FragmentDataTenderBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataTenderBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), DetailTenderActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton3.setOnClickListener {
            val intent = Intent(requireContext(), AddTenderActivity::class.java)
            startActivity(intent)
        }

        return root
    }

}