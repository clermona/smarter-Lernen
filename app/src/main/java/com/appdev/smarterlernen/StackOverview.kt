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
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.*


class StackOverview : Fragment() {
    private lateinit var recyclerView: RecyclerView

    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var items: List<Stack>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stack_overview, container, false)
        recyclerView = view.findViewById(R.id.listRecyclerView)

        database = AppDatabase.getInstance(requireContext())
        stackDao = database.stackDao()

        setupRecyclerView()

        return view
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun retrieveData() {
        runBlocking {
            launch(Dispatchers.Default) {
                items = stackDao.getAll()
            }
        }
    }

    private fun setupRecyclerView() {
        retrieveData()

        if(items.isNotEmpty()) {
            val adapter = StackAdapter(items) { item ->
                onStackItemClick(item)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
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