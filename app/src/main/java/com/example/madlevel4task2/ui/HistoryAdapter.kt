package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.FragmentHistoryViewBinding
import com.example.madlevel4task2.model.RockPaperScissors
import com.example.madlevel4task2.model.Win

class HistoryAdapter(private val history: List<RockPaperScissors>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_history_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(history[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = FragmentHistoryViewBinding.bind(itemView)

        fun databind(history: RockPaperScissors) {
            binding.ivCpu.setImageResource(getImageResource(history.computer))
            binding.ivPlayer.setImageResource(getImageResource(history.player))
            binding.tvResult.text = Win.values()[history.winner].name
            binding.tvDate.text = history.date
        }
    }
}