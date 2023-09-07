package com.example.tendery.ui.paket.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class PaketAdapter(private val paketList: ArrayList<PaketModel>) :
    RecyclerView.Adapter<PaketAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(paketModel: PaketModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_paket, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPaket = paketList[position]
        holder.tvPaketName.text = currentPaket.nama
        holder.tvPaketKode.text = currentPaket.kodeRup

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentPaket)
        }
    }

    override fun getItemCount(): Int {
        return paketList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPaketName: TextView = itemView.findViewById(R.id.nama_paket)
        val tvPaketKode: TextView = itemView.findViewById(R.id.kode_paket)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newPaketList: List<PaketModel>) {
        paketList.clear()
        paketList.addAll(newPaketList)
        notifyDataSetChanged()
    }
}