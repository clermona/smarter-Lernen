package com.appdev.smarterlernen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.R
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack

class CardAdapter(private val items: List<Card>, private val onItemClick: (Card) -> Unit) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_front, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]
        holder.buttonShowBack.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonShowBack: Button = itemView.findViewById(R.id.buttonShowBack)

    }
}

