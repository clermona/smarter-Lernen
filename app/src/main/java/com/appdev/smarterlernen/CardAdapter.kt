package com.appdev.smarterlernen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardAdapter(private val items: List<Card>, private val context: Context, private val onItemClick: (Card) -> Unit) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    lateinit var database: AppDatabase
    lateinit var stackDao: StackDao
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_front, parent, false)
        database = AppDatabase.getInstance(context)
        stackDao = database.stackDao()
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        runBlocking {
            launch(Dispatchers.Default) {
                holder.tVTitle.text= stackDao.getById(item.stackId).title.toString()

            }
        }

        holder.cardId.text=item.id.toString()
        holder.tVContent.text=item.frontSide

        holder.buttonShowBack.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonShowBack: Button = itemView.findViewById(R.id.buttonShowBack)
        val tVTitle: TextView = itemView.findViewById(R.id.textView2)
        val cardId: TextView = itemView.findViewById(R.id.cardId)
        val tVContent: TextView = itemView.findViewById(R.id.tVContent)

    }
}

