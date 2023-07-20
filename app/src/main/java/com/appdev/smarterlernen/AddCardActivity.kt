package com.appdev.smarterlernen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class AddCardActivity : AppCompatActivity()  {

    lateinit var binding: ActivityAddCardBinding
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao
    lateinit var stackDao: StackDao
    lateinit var stacks: List<Stack>
    var stackId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        getSupportActionBar()?.setHomeButtonEnabled(true);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        cardDao = database.cardDao()
        stackDao = database.stackDao()

        runBlocking {
            launch(Dispatchers.Default) {
                stacks = stackDao.getAll()
            }
        }

        val customAdapter = SpinnerStackAdapter(this, stacks)
        binding.spinner.adapter = customAdapter


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                stackId = customAdapter.getItem(position)?.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }

        binding.aaCardButton.setOnClickListener {

            val front = binding.frontText.text.toString()
            val back = binding.txtBack.text.toString()


            if(front != null && back != null && stackId != 0) {
                runBlocking {
                    launch(Dispatchers.Default) {
                        cardDao.insert(Card(stackId, front, back,0))
                    }
                }
                Toast.makeText(baseContext, " Created successfully", Toast.LENGTH_SHORT).show()

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

class SpinnerStackAdapter(context: Context, objects: List<Stack>) : ArrayAdapter<Stack>(context, 0, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        val stack = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = stack?.title

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val stack = getItem(position)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = stack?.title

        return view
    }
}