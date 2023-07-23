package com.appdev.smarterlernen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.appdev.smarterlernen.database.interfaces.StackDao
import com.appdev.smarterlernen.databinding.ActivityAddCardBinding
import com.appdev.smarterlernen.databinding.ActivityCardPreviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CardPreviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityCardPreviewBinding
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    lateinit var cardDao: CardDao
    lateinit var stack: Stack
    var cardId: Int = 0
    lateinit var cardList: List<Card>
    var tabIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_card_preview)


         binding = ActivityCardPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        database = AppDatabase.getInstance(this)
        stackDao = database.stackDao()
        cardDao = database.cardDao()

        tabIndex = 0

        binding.txtFront.isEnabled = true
        binding.txtBack.isEnabled = true

        val stackId = intent.getIntExtra("stack_id", 0)

        runBlocking {
            launch(Dispatchers.Default) {
                stack = stackDao.getById(stackId)
                cardList = cardDao.getByStackId(stackId)
            }
        }

        if(intent.getBooleanExtra("edit_mode", false)) {
            binding.btnPrevious.isVisible = false
            binding.btnNext.isVisible = false
            cardId = intent.getIntExtra("card_id", 0)
        }

        binding.stackName.text = stack.title

        if (cardList.isNotEmpty()) {
            updateCard(false)

            binding.pbCardProgress.max = cardList.size
            if (tabIndex == 0) {
                binding.pbCardProgress.progress = 1
            } else {
                binding.pbCardProgress.progress = tabIndex - 1
            }

            binding.btnSave.setOnClickListener {

                runBlocking {
                    saveCard()
                }

            }
            binding.btnDelete.setOnClickListener {
                if (cardList.isNotEmpty() && tabIndex >= 0 && tabIndex < cardList.size) {
                    val cardToDelete = cardList[tabIndex]

                    //Loeschoperation in einer Coroutine auf einem Hintergrundthread aus
                    GlobalScope.launch(Dispatchers.IO) {
                        cardDao.delete(cardToDelete)
                    }

                    cardList = cardList.toMutableList().apply { removeAt(tabIndex) }

                    if(intent.getBooleanExtra("edit_mode", false)) {
                        val intent = Intent(this, LearnActivity::class.java)
                        intent.putExtra("stackTitle", stack.title)
                        intent.putExtra("stackId", stack.id)
                        startActivity(intent)
                    }

                    if(cardList.isEmpty()) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        updateCard(false)
                    }
                }
            }

            binding.btnNext.setOnClickListener { updateCard(true) }

            binding.btnPrevious.setOnClickListener { updateCard(true, up = false) }
        }

    }

    private suspend fun saveCard() {
        val frontText = binding.txtFront.text.toString()
        val backText = binding.txtBack.text.toString()

        // Überpruefung, ob es eine gueltige Karte gibt, bevor speichern
        if (tabIndex >= 0 && tabIndex < cardList.size) {
            val cardToUpdate = cardList[tabIndex].copy(frontSide = frontText, backSide = backText)

            // Die Aktualisierungsoperation in einem Hintergrundthread ausführen
            withContext(Dispatchers.IO) {
                cardDao.update(cardToUpdate)
            }

            if(intent.getBooleanExtra("edit_mode", false)) {
                val intent = Intent(this, LearnActivity::class.java)
                intent.putExtra("stackTitle", stack.title)
                intent.putExtra("stackId", stack.id)
                startActivity(intent)
            } else {
                updateCard(false)
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

    private fun updateCard(changeIndex: Boolean = true, up: Boolean = true) {
        if (changeIndex) {
            if (up) {
                if (tabIndex < cardList.size - 1) {
                    tabIndex++
                }
            } else {
                if (tabIndex > 0) {
                    tabIndex--
                }
            }
        }

        if(cardId != 0) {
            tabIndex = cardList.indexOfFirst {it.id == cardId}
        }

        binding.btnPrevious.isEnabled = tabIndex != 0
        binding.btnNext.isEnabled = (tabIndex < cardList.size - 1)

        binding.pbCardProgress.progress = tabIndex + 1

        val cardNumberString = getString(R.string.label_card_number, tabIndex + 1, cardList.size)
        binding.cardNumber.text = cardNumberString

        // Daten aus der Datenbank neu abrufen
        runBlocking {
            launch(Dispatchers.Default) {
                cardList = cardDao.getByStackId(stack.id)
            }
        }

        // Die TextViews mit den Werten der aktuellen Karte aus der Liste aktualisieren
        val frontSide = cardList[tabIndex].frontSide
        val backSide = cardList[tabIndex].backSide

        // Pruefen, ob die Werte nicht null sind, bevor sie in den TextViews gesetzt werden
        if (frontSide != null && backSide != null) {
            binding.txtFront.text = Editable.Factory.getInstance().newEditable(frontSide)
            binding.txtBack.text = Editable.Factory.getInstance().newEditable(backSide)
        }
    }

    companion object {
        private const val EXTRA_STACK_ID = "extra_stack_id"

        fun newIntent(context: Context, stackId: Int): Intent {
            val intent = Intent(context, CardPreviewActivity::class.java)
            intent.putExtra(EXTRA_STACK_ID, stackId)
            return intent
        }
    }

}