package com.example.tendery.ui.pertanyaan.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class PertanyaanAdapter(private val pertanyaanList: ArrayList<PertanyaanModel>) :
    RecyclerView.Adapter<PertanyaanAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(pertanyaanModel: PertanyaanModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_pertanyaan, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPertanyaan = pertanyaanList[position]
        holder.tvPertanyaanKodeTender.text = currentPertanyaan.kodeTender
        holder.tvPertanyaan.text = currentPertanyaan.pertanyaan

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentPertanyaan)
        }
    }

    override fun getItemCount(): Int {
        return pertanyaanList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPertanyaanKodeTender: TextView = itemView.findViewById(R.id.kode_tender_pertanyaan)
        val tvPertanyaan: TextView = itemView.findViewById(R.id.pertanyaan)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newpertanyaanList: List<PertanyaanModel>) {
        pertanyaanList.clear()
        pertanyaanList.addAll(newpertanyaanList)
        notifyDataSetChanged()
    }
}