package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        val cardFrontFragment = CardFrontFragment()
        val cardBackFragment = CardBackFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.cardFrontContainer, cardFrontFragment)
            .replace(R.id.cardBackContainer, cardBackFragment)
            .commit()
    }
}