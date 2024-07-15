package com.example.myapplication.fragments.admin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.myapplication.classes.Ticket
import com.example.myapplication.classes.User
import com.example.myapplication.databinding.FragmentAdminReport2Binding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class AdminReport2Fragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var ticketList: ArrayList<Ticket> = ArrayList()
    private var userList: ArrayList<User> = ArrayList()
    private var chart: AnyChartView? = null
    private lateinit var binding: FragmentAdminReport2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Handle any arguments if needed
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminReport2Binding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = binding.pieChart
        getDataFirebase()
    }

    private fun genereateReports() {
        val pie: Pie = AnyChart.pie()
        var category = listOf(
            "Concert", "Festival", "Teatru", "Balet/Dans", "Expozitie",
            "Comedie", "Sport", "Street food", "Workshop", "Targ"
        )
        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        val concertNumber = ticketList.filter { it.category == "Concert" }.size
        val festivalNumber = ticketList.filter { it.category == "Festival" }.size
        val teatruNumber = ticketList.filter { it.category == "Teatru" }.size
        val baletNumber = ticketList.filter { it.category == "Balet/Dans" }.size
        val expozitieNumber = ticketList.filter { it.category == "Expozitie" }.size
        val comedieNumber = ticketList.filter { it.category == "Comedie" }.size
        val sportNumber = ticketList.filter { it.category == "Sport" }.size
        val streetFoodNumber = ticketList.filter { it.category == "Street food" }.size
        val workshopNumber = ticketList.filter { it.category == "Workshop" }.size
        val targNumber = ticketList.filter { it.category == "Targ" }.size

        val numberOfCategory = listOf(
            concertNumber,
            festivalNumber,
            teatruNumber,
            baletNumber,
            expozitieNumber,
            comedieNumber,
            sportNumber,
            streetFoodNumber,
            workshopNumber,
            targNumber
        )

        for (index in numberOfCategory.indices) {
            dataPieChart.add(
                ValueDataEntry(
                    category.elementAt(index),
                    numberOfCategory.elementAt(index)
                )
            )
        }

        pie.data(dataPieChart)
        pie.title("Distributia categoriilor in functie de biletele cumparate")
        chart!!.setChart(pie)
    }

    private fun getDataFirebase() {
        db.collection("user").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                userList.clear() // Clear the userList to avoid duplicates
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        userList.add(dc.document.toObject(User::class.java))
                    }
                }

                if (userList.isNotEmpty()) {
                    for (user in userList) {
                        val ticketList2 = user.tickets
                        if (ticketList2 != null) {
                            ticketList.addAll(ticketList2)
                        }
                        Log.d("TicketListUpdated", "Tickets updated for user: ${user.email}")
                    }
                    genereateReports() // Generate the report after data is loaded
                } else {
                    Log.e("NullUserList", "userList is null")
                }

                Log.d("TicketListUpdated2", ticketList.toString())
            }
        })
    }
}
