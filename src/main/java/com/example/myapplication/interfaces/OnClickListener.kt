package com.example.myapplication.interfaces

import com.example.myapplication.classes.Ticket

//click on a single ticket and return ticket position
interface OnClickListener {
    fun onClickListenerDetails(ticketPos: Int)
}