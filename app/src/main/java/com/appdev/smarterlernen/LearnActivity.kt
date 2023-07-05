package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        val cardFrontFragment = CardFrontFragment()
        //val cardBackFragment = CardBackFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.cardFragmentContainer, cardFrontFragment)
            //.replace(R.id.cardBackContainer, cardBackFragment)
            .commit()
    }
}