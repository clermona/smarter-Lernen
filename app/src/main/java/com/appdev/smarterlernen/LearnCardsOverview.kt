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
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LearnCardsOverview: Fragment() {
    private lateinit var recyclerView: RecyclerView
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var cardDao: CardDao
    lateinit var items: List<Card>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_learn_cards, container, false)
        recyclerView = view.findViewById(R.id.listRecyclerView2)

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
                items = cardDao.getAll()

            }
        }
    }


    private fun setupRecyclerView() {
        retrieveData()

        if (items.isNotEmpty()) {
            val adapter = CardAdapter(items,requireContext()) { item ->


                    val fragmentManager = requireActivity().supportFragmentManager
                    fragmentManager.beginTransaction()
                        .replace(R.id.cardFragmentContainer, CardBackFragment())
                        .addToBackStack(null)
                        .commit()
                }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}