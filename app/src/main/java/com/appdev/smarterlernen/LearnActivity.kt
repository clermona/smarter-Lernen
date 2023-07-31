package com.appdev.smarterlernen

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.appdev.smarterlernen.databinding.ActivityLearnBinding


class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        var binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

