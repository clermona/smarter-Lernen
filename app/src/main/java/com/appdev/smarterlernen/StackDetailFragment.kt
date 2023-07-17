package com.appdev.smarterlernen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.appdev.smarterlernen.database.entities.Stack
class StackDetailFragment : Fragment() {

    private lateinit var selectedStackTitle: String
    private var stackId: Int = 0
    lateinit var buttonLearn: Button
    lateinit var buttonPreview: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedStackTitle = it.getString("selectedStack") ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stack_detail, container, false)

        val stackNameTextView = view.findViewById<TextView>(R.id.stackName)
        val selectedStack = arguments?.getParcelable<Stack>("selectedStack")
        stackNameTextView.text = selectedStack?.title

        if (selectedStack != null) {
            stackId = selectedStack.id
        }

        buttonLearn = view.findViewById<Button>(R.id.buttonLernen)
        buttonPreview = view.findViewById(R.id.buttonVorschau)

        buttonLearn.setOnClickListener {
            // Start the new activity here
            onLearnButtonClick()
        }

        buttonPreview.setOnClickListener {
            onPreviewButtonClick()
        }

        return view
    }
    private fun onLearnButtonClick() {
        val intent = Intent(requireContext(), LearnActivity::class.java)
        startActivity(intent)
    }

    private fun onPreviewButtonClick() {
        val intent = Intent(requireContext(), CardPreviewActivity::class.java)
        intent.putExtra("stack_id", stackId)
        startActivity(intent)
    }

}

