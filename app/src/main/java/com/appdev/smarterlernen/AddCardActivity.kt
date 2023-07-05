package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import com.appdev.smarterlernen.databinding.ActivityMainBinding

class AddCardActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aaCardButton.setOnClickListener {

        }
    }
}