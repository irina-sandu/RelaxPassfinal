package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.adapterBuyTicket.BuyTicketAdapter
import com.example.myapplication.classes.Ticket
import com.example.myapplication.classes.User
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class HistoryFragment : Fragment() {

    private var ticketList: ArrayList<Ticket>? = ArrayList()
    private var userList: ArrayList<User>? = ArrayList()
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: BuyTicketAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Handle any arguments if needed
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFirebase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }

    private fun getDataFirebase() {
        if (!isAdded) {
            Log.e("FragmentError", "Fragment not attached to a context.")
            return
        }

        val sharedPreferences = requireContext().getSharedPreferences(
            Utils.NAME_FOLDER_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val emailUser: String? = sharedPreferences.getString(Utils.EMAIL_KEY, null)

        db.collection("user").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }

                userList?.clear() // Clear the userList to avoid duplicates
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        userList?.add(dc.document.toObject(User::class.java))
                    }
                }

                if (userList != null) {
                    for (user in userList!!) {
                        if (emailUser == user.email) {
                            ticketList = user.tickets
                            Log.d("TicketListUpdated", "Tickets updated for user: ${user.email}")
                            updateRecyclerView()
                        }
                    }
                } else {
                    Log.e("NullUserList", "userList is null")
                }
            }
        })
    }

    private fun updateRecyclerView() {
        if (isAdded) {
            binding.recycleviewTicketItemFragmentHistory.layoutManager =
                LinearLayoutManager(requireContext())
            adapter = BuyTicketAdapter(ticketList ?: ArrayList())
            binding.recycleviewTicketItemFragmentHistory.adapter = adapter
        }
    }

}