package com.example.myapplication.classes

class Event {
    data class Event(
        val EventTitle: String,
        val Event: String,
        val EventDetails: String,
        val EventUrlTolmage: String,
        val EventLocation: Location,
        val PriceCategoryOne: Int,
        val PriceCategoryTwo: Int,
        val PriceCategoryThree: Int,
        val PriceCategoryVIP: Int,
        val EventNumberTickets: Int?
    )
}