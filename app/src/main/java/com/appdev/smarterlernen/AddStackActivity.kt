package com.appdev.smarterlernen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import com.appdev.smarterlernen.databinding.ActivityAddStackBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddStackActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddStackBinding
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var tvStackTitel:EditText
    lateinit var btnAdd:Button
    lateinit var btnAddCard:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stack)

         binding = ActivityAddStackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
         tvStackTitel = binding.addStackText
        btnAdd = binding.createStackbutton
        btnAddCard = binding.addCardsButton
        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()
        btnAdd.isEnabled=false
        btnAddCard.isEnabled=false
        tvStackTitel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Update button states based on the text in the EditText
                val isTextEmpty = s.isNullOrBlank()
                btnAdd.isEnabled = !isTextEmpty
                btnAddCard.isEnabled = !isTextEmpty
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.addCardsButton.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }
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