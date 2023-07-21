package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import com.appdev.smarterlernen.databinding.ActivityAddStackBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddStackActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddStackBinding
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stack)

         binding = ActivityAddStackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()


        binding.createStackbutton.setOnClickListener {
            val title: String = binding.addStackText.text.toString()

            if(title != null) {
                runBlocking {
                    launch(Dispatchers.Default) {
                        if(stackDao.getByTitle(title)  != null) {
                            stackDao.update(Stack(title,0))

                        } else {
                            stackDao.insert(Stack(title,0))


                        }
                    }
                }
                Toast.makeText(applicationContext, " Created successfully", Toast.LENGTH_SHORT).show()


            }
        }

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