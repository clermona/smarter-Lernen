package com.appdev.smarterlernen

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StackDetailFragment : Fragment() {

    private lateinit var selectedStackTitle: String

    lateinit var buttonLearn: Button
    private lateinit var newCards: TextView
    private lateinit var usedCards: TextView
    lateinit var stackTitle: TextView
     var stackId: Int = 0
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao
    lateinit var allCards: List<Card>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedStackTitle = it.getString("selectedStack") ?: ""


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stack_detail, container, false)

        val stackNameTextView = view.findViewById<TextView>(R.id.selectedStackTextView)

        val selectedStack = arguments?.getParcelable<Stack>("selectedStack")
        stackNameTextView.text = selectedStack?.title
        stackId = selectedStack?.id?:0
        database = AppDatabase.getInstance(requireActivity())
        cardDao = database.cardDao()
        newCards=view.findViewById<Button>(R.id.textViewNeu)
        usedCards=view.findViewById<Button>(R.id.textViewWiederholen)
        buttonLearn = view.findViewById<Button>(R.id.buttonLernen)
        buttonLearn.setOnClickListener {
            // Start the new activity here
            onLearnButtonClick()
        }


        stackTitle= view.findViewById<TextView>(R.id.selectedStackTextView)


        runBlocking {
            launch(Dispatchers.Default) {
                allCards=cardDao.getAll()

            }
        }

        newCards.text= "Neu: "+allCards.filter{it.rating == 0}.count().toString()
        usedCards.text= "Wiederholen: "+allCards.filter{it.rating > 0}.count().toString()
        return view
    }
    private fun onLearnButtonClick() {
        val intent = Intent(requireContext(), LearnActivity::class.java)


        intent.putExtra("stackTitle", stackTitle.text.toString())
        intent.putExtra("stackId", stackId)
        startActivity(intent)
    }

}

