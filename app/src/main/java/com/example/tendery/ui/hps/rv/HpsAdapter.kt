package com.example.tendery.ui.hps.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R


class HpsAdapter(private val hpsList: ArrayList<HpsModel>) :
    RecyclerView.Adapter<HpsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(hpsModel: HpsModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_hps, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHps = hpsList[position]
        holder.tvHpsName.text = currentHps.jenisBarangJasa
        holder.tvHpsKode.text = currentHps.kodeRup

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentHps)
        }
    }

    override fun getItemCount(): Int {
        return hpsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHpsName: TextView = itemView.findViewById(R.id.jenis_barang)
        val tvHpsKode: TextView = itemView.findViewById(R.id.kode_RUP)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newHpsList: List<HpsModel>) {
        hpsList.clear()
        hpsList.addAll(newHpsList)
        notifyDataSetChanged()
    }
}