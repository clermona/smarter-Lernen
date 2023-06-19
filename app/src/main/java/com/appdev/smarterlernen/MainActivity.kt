package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.appdev.smarterlernen.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initial binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout=binding.tabLayout
        viewPager=binding.viewPager

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(StackOverview(), "Stapel")
        adapter.addFragment(CardManagement(), "Verwaltung")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }
}