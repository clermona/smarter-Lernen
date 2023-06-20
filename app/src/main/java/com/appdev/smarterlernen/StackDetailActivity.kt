package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.appdev.smarterlernen.databinding.ActivityMainBinding
import com.appdev.smarterlernen.databinding.ActivityStackDetailBinding

class StackDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityStackDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_detail)
        //initial binding
        binding = ActivityStackDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the selected stack item from the intent extras
        val selectedStack = intent.getStringExtra("selectedStack")

        // Display the selected stack item
        binding.selectedStackTextView.text = selectedStack


    }
}