package com.example.tendery.ui.preTest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class SoalAdapter(val context: Context, val question: Soal) :
    RecyclerView.Adapter<SoalAdapter.OptionViewHolder>() {

    private var options: List<String> = listOf(question.A, question.B, question.C)

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var optionView = itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_soal, parent, false)
        return  OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionView.text = options[position]
        holder.itemView.setOnClickListener {
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }
        if(question.userAnswer == options[position]){
            holder.itemView.setBackgroundResource(R.drawable.item_selected_bg)
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.uption_item_bg)
        }

    }

    override fun getItemCount(): Int {
        return options.size
    }
}