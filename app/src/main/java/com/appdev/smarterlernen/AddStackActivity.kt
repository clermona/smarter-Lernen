package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()

        binding.createStackbutton.setOnClickListener {
            val title: String = binding.addStackText.text.toString()

            if(title != null) {
                runBlocking {
                    launch(Dispatchers.Default) {
                        if(stackDao.getByTitle(title)  != null) {
                            stackDao.update(Stack(title))
                        } else {
                            stackDao.insert(Stack(title))
                        }
                    }
                }

            }
        }
    }
}