package com.appdev.smarterlernen

import CardSnapHelper
import android.content.Intent
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
     var stackId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_learn_cards, container, false)
        recyclerView = view.findViewById(R.id.listRecyclerView2)
        arguments?.let {
             stackId = it.getInt("selectedCards")
        }
        database = AppDatabase.getInstance(requireContext())
        stackDao = database.stackDao()
        cardDao = database.cardDao()
        setupRecyclerView()
        val snapHelper = CardSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        return view
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }
    private fun retrieveData() {
        runBlocking {
            launch(Dispatchers.Default) {
                items = cardDao.getByStackId(stackId)

            }
        }

    }


    private fun setupRecyclerView() {
        retrieveData()

        if (items.isNotEmpty()) {

            items=items.sortedByDescending { it.rating }


            val adapter = CardAdapter(items,requireContext(),  { item ->
                val fragmentManager = requireActivity().supportFragmentManager
                val newFragment = CardBackFragment()

                val bundle = Bundle()
                bundle.putSerializable("currentCard", item)
                newFragment.arguments = bundle


                fragmentManager.beginTransaction()
                    .replace(R.id.cardFragmentContainer, newFragment)
                    .addToBackStack(null)
                    .commit()


            }, {item->
                val intent = Intent(requireContext(), CardPreviewActivity::class.java)
                //boolean fuer edit Modus, wenn in edit dann sind die Buttons Prev/ Next nicht sichtbar
                intent.putExtra("stack_id", item.stackId)
                intent.putExtra("edit_mode", true)
                intent.putExtra("card_id", item.id)
                startActivity(intent)
            })

            adapter.updateData(items)
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }

}


