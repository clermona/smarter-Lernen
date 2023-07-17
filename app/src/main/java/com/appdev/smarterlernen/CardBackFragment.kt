package com.appdev.smarterlernen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CardBackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardBackFragment : Fragment()   {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: ActivityAddCardBinding
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao
    lateinit var stackDao: StackDao
    lateinit var stacks: List<Stack>
    var stackId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        database = AppDatabase.getInstance(requireActivity())
        stackDao = database.stackDao()
        runBlocking {
            launch(Dispatchers.Default) {
                stacks = stackDao.getAll()
            }
        }

        cardDao = database.cardDao()
        val customAdapter = SpinnerStackAdapter(requireActivity(), stacks)
        binding.spinner.adapter = customAdapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                stackId = customAdapter.getItem(position)?.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_back, container, false)
        val buttonLeicht = view.findViewById<Button>(R.id.buttonLeicht)
        val buttonMittel = view.findViewById<Button>(R.id.buttonMittel)
        val buttonSchwer = view.findViewById<Button>(R.id.buttonSchwer)
        val cardId = view.findViewById<TextView>(R.id.cardId)

        val backSide = CardBackFragment()

        buttonLeicht.setOnClickListener {
            runBlocking {
                launch(Dispatchers.Default) {

                    val cardIdString = cardId.text.toString()
                    val cardIdInt = cardIdString.toInt()
                    val updateCard = cardDao.getById(cardIdInt)
                    updateCard.rating= "Leicht";
                    cardDao.update(updateCard)
                }
            }
        }
        buttonMittel.setOnClickListener {
            val cardIdString = cardId.text.toString()
            val cardIdInt = cardIdString.toInt()
            val updateCard = cardDao.getById(cardIdInt)
            updateCard.rating= "Mittel";
            cardDao.update(updateCard)
        }
        buttonSchwer.setOnClickListener {
            val cardIdString = cardId.text.toString()
            val cardIdInt = cardIdString.toInt()
            val updateCard = cardDao.getById(cardIdInt)
            updateCard.rating= "Schwer";
            cardDao.update(updateCard)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_back, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardBackFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardBackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}