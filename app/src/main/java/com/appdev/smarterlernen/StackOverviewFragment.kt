package com.appdev.smarterlernen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.*


class StackOverviewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var cardDao: CardDao
    lateinit var items: List<Stack>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stack_overview, container, false)
        recyclerView = view.findViewById(R.id.listRecyclerView)

        database = AppDatabase.getInstance(requireContext())
        stackDao = database.stackDao()
        cardDao = database.cardDao()
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

        if (items.isNotEmpty()) {
            val adapter = StackAdapter(items,requireContext()) { item ->
                onStackItemClick(item)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onStackItemClick(selectedStack: Stack) {
           // Create an instance of the StackDetailFragment and pass the selectedStack as arguments
            val stackDetailFragment = StackDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("selectedStack", selectedStack)
            stackDetailFragment.arguments = bundle
        runBlocking {
            launch(Dispatchers.Default) {
                items = stackDao.getAll()

            }
        }
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, stackDetailFragment)
                .addToBackStack(null)
                .commit()


    }

}