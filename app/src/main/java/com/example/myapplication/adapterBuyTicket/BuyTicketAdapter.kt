package com.example.myapplication.adapterBuyTicket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.classes.Ticket
import com.example.myapplication.databinding.TicketBuyItemBinding

class BuyTicketAdapter(
    private var tickets: ArrayList<Ticket>,
): RecyclerView.Adapter<BuyTicketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyTicketViewHolder {
        val binding = TicketBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyTicketViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: BuyTicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }
}