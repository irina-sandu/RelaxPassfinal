package com.example.myapplication.adapterBuyTicket

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.classes.Ticket
import com.example.myapplication.databinding.TicketBuyItemBinding
import com.squareup.picasso.Picasso

class BuyTicketViewHolder(
    private val binding: TicketBuyItemBinding
):  RecyclerView.ViewHolder(binding.root){
    val cardTicketView = binding.cardBuyTicket
    fun bind(item: Ticket) {
        binding.tvNameTicket.text = item.title
        binding.tvDateTicket.text = firstTenCaracters(item.data)
        binding.tvLocationTicket.text = "${item.location}, ${item.city}"
        setImage(item)

    }

    private fun firstTenCaracters(date: String): String {
        return date.substring(0, 10)
    }

    private fun setImage(item: Ticket) {
        Picasso.get().load(item.urlToImage).into(binding.imageTicket)
    }
}