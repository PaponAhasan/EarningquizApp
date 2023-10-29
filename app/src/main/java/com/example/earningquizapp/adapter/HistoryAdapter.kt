package com.example.earningquizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earningquizapp.databinding.CategoryitemBinding
import com.example.earningquizapp.databinding.HistoryitemBinding
import com.example.earningquizapp.model.History
import java.sql.Timestamp
import java.util.Date

class HistoryAdapter(private val history: MutableList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(var binding: HistoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = HistoryitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = HistoryViewHolder(view)
        return viewHolder
    }

    override fun getItemCount() = history.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = history[position]
        holder.binding.tvHtime.text =
            Date(Timestamp(currentItem.timeAndDate.toLong()).time).toString()
        holder.binding.tvHcoin.text = (if (currentItem.isWithdrawal) {
            "- ${currentItem.coin}"
        } else {
            "+ ${currentItem.coin}"
        })

        holder.binding.tvCoinType.text = (if (currentItem.isWithdrawal) {
            "- Money Withdraw"
        } else {
            "+ Money Added"
        })
    }
}