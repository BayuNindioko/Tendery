package com.example.tendery.ui.hps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tendery.databinding.FragmentHpsBinding
import com.example.tendery.ui.hps.addHPS.AddHPSActivity
import com.example.tendery.ui.hps.detailHPS.Rincian_hpsActivity
import com.example.tendery.ui.paket.addPaket.AddPaketActivity
import com.example.tendery.ui.paket.detail.DetailPaketActivity

class HpsFragment : Fragment() {

    private var _binding: FragmentHpsBinding? = null

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

        binding.cardUser.setOnClickListener {
            val intent = Intent(requireContext(), Rincian_hpsActivity::class.java)
            startActivity(intent)
        }

        binding.floatingActionButton3.setOnClickListener {
            val intent = Intent(requireContext(), AddHPSActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}