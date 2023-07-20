package com.appdev.smarterlernen

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout

    private lateinit var viewPager: ViewPager
    lateinit var binding: ActivityMainBinding

    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var cardDao: CardDao
    lateinit var stacks: List<Stack>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //initial binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        // TO BE DELETED
        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()
        cardDao = database.cardDao()

        val list = listOf(Stack("AppDev",0), Stack("SecLab",0))

        for (test in list) {
            runBlocking {
                launch(Dispatchers.Default) {
                    if (test.title?.let { stackDao.getByTitle(it) } != null) {
                        stackDao.update(test)



                    } else {
                        stackDao.insert(test)
                    }

                }
            }
        }


        tabLayout = binding.tabLayout
        viewPager = binding.viewPager


        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(
            StackOverviewFragment(),
            resources.getString(R.string.page_title_stacks)
        )
        adapter.addFragment(CardManagement(), resources.getString(R.string.page_title_management))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)




    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_addcard -> {
                val intent = Intent(this, AddCardActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_addstack -> {
                val intent = Intent(this, AddStackActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}