package com.example.tendery.ui.pertanyaan.jawaban

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class JawabanAdapter (var listener: OnItemClickListener) : RecyclerView.Adapter<JawabanAdapter.ViewHolder>() {

    private val data: MutableList<List<String>> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClick(kode: String, pertanyaan: String)
    }

    // Fungsi setter untuk listener
    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    fun setData(newData: List<List<String>>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_pertanyaan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]

        holder.kodePertanyaanTextView.text = currentItem[2]
        holder.jawabanTextView.text = currentItem[3]

        holder.itemView.setOnClickListener {
            val kode = currentItem[2]
            val pertanyaan = currentItem[3]
            listener.onItemClick(kode, pertanyaan) // Panggil fungsi onItemClick di listener
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kodePertanyaanTextView: TextView = itemView.findViewById(R.id.kode_tender_pertanyaan)
        val jawabanTextView: TextView = itemView.findViewById(R.id.pertanyaan)
    }
}
