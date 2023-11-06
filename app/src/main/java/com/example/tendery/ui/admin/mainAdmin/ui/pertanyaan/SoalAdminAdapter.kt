package com.example.tendery.ui.admin.mainAdmin.ui.pertanyaan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R
import com.example.tendery.ui.preTest.Soal

class SoalAdminAdapter(private val listSoalJawaban: List<Soal>, private val itemClickListener: (Soal) -> Unit) : RecyclerView.Adapter<SoalAdminAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val soalText: TextView = itemView.findViewById(R.id.soal)
        val jawabanText: TextView = itemView.findViewById(R.id.jawaban)
        val pilihanA: TextView = itemView.findViewById(R.id.pilihanA)
        val pilihanB: TextView = itemView.findViewById(R.id.pilihanB)
        val pilihanC: TextView = itemView.findViewById(R.id.pilihanC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_soal_jawaban, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSoalJawaban = listSoalJawaban[position]
        holder.soalText.text = currentSoalJawaban.soal
        holder.jawabanText.text = "Jawaban: ${currentSoalJawaban.jawaban}"
        holder.pilihanA.text = "A: ${currentSoalJawaban.A}"
        holder.pilihanB.text = "B: ${currentSoalJawaban.B}"
        holder.pilihanC.text = "A: ${currentSoalJawaban.C}"

        holder.itemView.setOnClickListener {
            itemClickListener(currentSoalJawaban)
        }
    }

    override fun getItemCount(): Int {
        return listSoalJawaban.size
    }

}