package com.example.tendery.ui.admin.mainAdmin.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tendery.R

class InfoAdapter(
    private val users: List<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<InfoAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = users[position]
        holder.bind(habit)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvUsername: TextView = itemView.findViewById(R.id.username_card)
        private val tvRole: TextView = itemView.findViewById(R.id.role_card)
        private val tvPre: TextView = itemView.findViewById(R.id.nilaiPre_card)
        private val tvPost: TextView = itemView.findViewById(R.id.nilaiPost_card)

        fun bind(user: User) {
            tvUsername.text = user.FullName
            tvRole.text = user.Role
            tvPre.text = "Nilai Pre-Test\t\t: " + user.PreTest.toString()
            tvPost.text = "Nilai Post-Test\t: " + user.PostTest.toString()

        }

    }
}
