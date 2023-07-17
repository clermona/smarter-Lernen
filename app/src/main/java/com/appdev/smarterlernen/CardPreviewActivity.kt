package com.appdev.smarterlernen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddStackBinding
import com.appdev.smarterlernen.databinding.ActivityCardPreviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardPreviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardPreviewBinding
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var cardDao: CardDao
    lateinit var stack: Stack
    lateinit var cardList: List<Card>
    var tabIndex: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_preview)

        binding = ActivityCardPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()
        cardDao = database.cardDao()

        tabIndex = 0

        val stackId = intent.getIntExtra("stack_id", 0)

        runBlocking {
            launch(Dispatchers.Default) {
                stack = stackDao.getById(stackId)
                cardList = cardDao.getByStackId(stackId)
            }
        }

        binding.stackName.text = stack.title


        if(cardList.isNotEmpty()) {
            binding.txtFront.text = cardList[tabIndex].frontSide
            binding.txtBack.text = cardList[tabIndex].backSide

            binding.pbCardProgress.max = cardList.size

            if(tabIndex == 0) {
                binding.pbCardProgress.progress = 1
            } else {
                binding.pbCardProgress.progress = tabIndex - 1
            }


            binding.cardNumber.text = "Karte ${tabIndex+1} von ${cardList.size}"

            binding.btnNext.setOnClickListener { updateCard(true) }

            binding.btnPrevious.setOnClickListener { updateCard(false) }
        }
    }

    private fun updateCard(up: Boolean) {
        if(up) {
            if(tabIndex < cardList.size - 1) {
                tabIndex++
            }
        } else {
            if(tabIndex > 0) {
                tabIndex--
            }
        }

        binding.txtFront.text = cardList[tabIndex].frontSide
        binding.txtBack.text = cardList[tabIndex].backSide

        binding.pbCardProgress.progress = tabIndex + 1

        binding.cardNumber.text = "Karte ${tabIndex+1} von ${cardList.size}"
    }

}