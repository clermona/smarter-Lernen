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
        val backSide = CardBackFragment()
        val title = requireActivity().intent.getStringExtra("stackTitle")
        val stackId = requireActivity().intent.getStringExtra("stackId")


        stackTitel.text= title;


        button.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.cardFragmentContainer, backSide)
                .addToBackStack(null)
                .commit()
        }

        // Inflate the layout for this fragment
        return view
    }
}