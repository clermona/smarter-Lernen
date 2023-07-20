package com.appdev.smarterlernen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        getSupportActionBar()?.setHomeButtonEnabled(true);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val intent = intent
        val stackId= intent.getStringExtra("stackId")
        val learnCardsOverview = LearnCardsOverview()
        val bundle = Bundle()
        bundle.putString("selectedCards",stackId)
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

