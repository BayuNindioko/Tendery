package com.example.tendery.ui.data_tender.rv

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class DataTenderAdapter(private val dataTenderList: ArrayList<DataModel>) :
    RecyclerView.Adapter<DataTenderAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(dataModel: DataModel)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_data_tender, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDataTender = dataTenderList[position]
        holder.tvDataTenderNama.text = currentDataTender.nama
        holder.tvDataTenderKode.text = currentDataTender.kodeTender


        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentDataTender)
        }
    }

    override fun getItemCount(): Int {
        return dataTenderList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDataTenderNama: TextView = itemView.findViewById(R.id.nama_tender)
        val tvDataTenderKode: TextView = itemView.findViewById(R.id.kode_tender_data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newdataTenderList: List<DataModel>) {
        dataTenderList.clear()
        dataTenderList.addAll(newdataTenderList)
        notifyDataSetChanged()
    }
}