package com.appdev.smarterlernen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class StackOverview : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stack_overview, container, false)
        recyclerView = view.findViewById(R.id.listRecyclerView)
        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView() {
        val items = listOf("Lineare Optimierung", "Stochastik", "Grundbegriffe des Öffentlichen Rechts und Privatrechts", "App Development")
        val adapter = StackAdapter(items) { item ->
            // Handle the click event for the list item
            onStackItemClick(item)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onStackItemClick(item: String) {
        // Handle the click event for the stack item
        Toast.makeText(requireContext(), "Clicked: $item", Toast.LENGTH_SHORT).show()

        // Open the stack detail activity
        val intent = Intent(requireContext(), StackDetailActivity::class.java)
        intent.putExtra("selectedStack", item)
        startActivity(intent)
    }
}