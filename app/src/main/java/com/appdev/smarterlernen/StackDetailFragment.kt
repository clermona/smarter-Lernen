package com.appdev.smarterlernen

import android.content.Intent
import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class StackDetailFragment : Fragment() {

    private lateinit var selectedStackTitle: String

    lateinit var buttonLearn: Button
    private lateinit var newCards: TextView
    private lateinit var usedCards: TextView
    lateinit var stackTitle: TextView
    var stackId: Int = 0
    lateinit var database: AppDatabase

    lateinit var buttonPreview: Button

    lateinit var cardDao: CardDao
    lateinit var allCards: List<Card>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedStackTitle = it.getString("selectedStack") ?: "Select a stack"


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stack_detail, container, false)
        newCards = view.findViewById<TextView>(R.id.textViewNeu)
        usedCards = view.findViewById<TextView>(R.id.textViewWiederholen)

        arguments?.let {
            selectedStackTitle = it.getString("selectedStack") ?: "Select a stack"
            newCards.text = ""
            usedCards.text = ""

        }
        val stackNameTextView = view.findViewById<TextView>(R.id.stackName)
        val selectedStack = arguments?.getParcelable<Stack>("selectedStack")
        stackNameTextView.text = selectedStack?.title
        stackId = selectedStack?.id ?: 0


        if (selectedStack != null) {
            stackId = selectedStack.id
        }

        database = AppDatabase.getInstance(requireContext())
        cardDao = database.cardDao()

        buttonLearn = view.findViewById(R.id.buttonLernen)
        buttonLearn.isEnabled=false
        buttonPreview = view.findViewById(R.id.buttonVorschau)

        runBlocking {
            val cardCount = withContext(Dispatchers.IO) {
                cardDao.getCountByStackId(stackId)
            }
            buttonPreview.isEnabled = cardCount != 0
            buttonLearn.isEnabled = cardCount != 0
        }

        buttonLearn.setOnClickListener {
            // Start the new activity here
            onLearnButtonClick()
        }
        newCards.text = ""
        usedCards.text = ""

        buttonPreview.setOnClickListener {
            onPreviewButtonClick()
        }


        stackTitle = view.findViewById<TextView>(R.id.stackName)





        updateCardCounts()

        return view
    }
        private fun updateCardCounts() {
            runBlocking {
                launch(Dispatchers.Default) {

                    allCards = cardDao.getByStackId(stackId)
                    val sumNew = allCards.filter { it.rating == 0 }.count().toString()
                    val sumUsed = allCards.filter { it.rating >= 0 }.count().toString()

                    // Update the TextViews with the card counts
                    newCards.text = "Neu: $sumNew"
                    usedCards.text = "Wiederholen: $sumUsed"
                }
            }
        }

        private fun onLearnButtonClick() {
            val intent = Intent(requireContext(), LearnActivity::class.java)
            intent.putExtra("stackTitle", stackTitle.text.toString())
            intent.putExtra("stackId", stackId)
            startActivity(intent)
        }

        private fun onPreviewButtonClick() {
            val intent = Intent(requireContext(), CardPreviewActivity::class.java)
            intent.putExtra("stack_id", stackId)
            startActivity(intent)
        }
    }