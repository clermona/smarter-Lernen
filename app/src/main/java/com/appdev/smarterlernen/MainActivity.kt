package com.appdev.smarterlernen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        val fabAdd = binding.fabAdd
        val fabAddCard = binding.fabAddCard
        val fabAddStack = binding.fabAddStack
        val txtFabCard = binding.txtAddCard
        val txtFabStack = binding.txtAddStack

        var isAllFabsVisible = false

        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(StackOverview(), resources.getString(R.string.page_title_stacks))
        adapter.addFragment(CardManagement(), resources.getString(R.string.page_title_management))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        fabAdd.setOnClickListener{
            if(!isAllFabsVisible) {
                fabAddCard.show()
                fabAddStack.show()
                txtFabCard.visibility = View.VISIBLE
                txtFabStack.visibility = View.VISIBLE
                isAllFabsVisible = true
            } else {
                fabAddCard.hide()
                fabAddStack.hide()
                txtFabCard.visibility = View.INVISIBLE
                txtFabStack.visibility = View.INVISIBLE
                isAllFabsVisible = false
            }
        }

        fabAddCard.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }

        fabAddStack.setOnClickListener{
            val intent = Intent(this, AddStackActivity::class.java)
            startActivity(intent)
        }

    }
}