package com.appdev.smarterlernen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class CardFrontFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_card_front, container, false)
        val button = view.findViewById<Button>(R.id.buttonShowBack)
        val stackTitel = view.findViewById<TextView>(R.id.textView2)
        val rating = view.findViewById<TextView>(R.id.rating)
        val cardId = view.findViewById<TextView>(R.id.cardId)

        val backSide = CardBackFragment()
        val title = requireActivity().intent.getStringExtra("stackTitle")

        stackTitel.text= title;




        // Inflate the layout for this fragment
        return view
    }
}