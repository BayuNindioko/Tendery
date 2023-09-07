package com.example.tendery.ui.penawaran.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R


class PenawaranAdapter(private val penawaranList: ArrayList<PenawaranModel>) :
    RecyclerView.Adapter<PenawaranAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(penawaranModel: PenawaranModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_penawaran, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPenawaran = penawaranList[position]
        holder.tvPenawaranKode.text = currentPenawaran.kodePenawaran
        holder.tvPenawaranHarga.text = currentPenawaran.hargaPenawaran
        holder.tvStatus.text = currentPenawaran.status

        if (currentPenawaran.status == "Dipilih") {
            holder.tvStatus.setTextColor(holder.itemView.resources.getColor(R.color.green)) // Change to your desired green color
        } else {
            holder.tvStatus.setTextColor(holder.itemView.resources.getColor(R.color.primary)) // Change to your desired blue color
        }


        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentPenawaran)
        }
    }

    override fun getItemCount(): Int {
        return penawaranList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPenawaranKode: TextView = itemView.findViewById(R.id.kode_tender)
        val tvPenawaranHarga: TextView = itemView.findViewById(R.id.harga_penawaran)
        val tvStatus: TextView = itemView.findViewById(R.id.status)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newpenawaranList: List<PenawaranModel>) {
        penawaranList.clear()
        penawaranList.addAll(newpenawaranList)
        notifyDataSetChanged()
    }
}