package com.appdev.smarterlernen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        getSupportActionBar()?.setDisplayShowCustomEnabled(false);
        val intent = intent
        val stackId = intent.getIntExtra("stackId", 0)
        val learnCardsOverview = LearnCardsOverview()
        val bundle = Bundle()
        bundle.putInt("selectedCards",stackId)
        learnCardsOverview.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.cardFragmentContainer, learnCardsOverview)
            //.replace(R.id.cardBackContainer, cardBackFragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

