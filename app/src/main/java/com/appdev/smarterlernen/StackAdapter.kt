package com.appdev.smarterlernen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.R
import com.appdev.smarterlernen.database.entities.Stack

class StackAdapter(private val items: List<Stack>, private val onItemClick: (Stack) -> Unit) : RecyclerView.Adapter<StackAdapter.StackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return StackViewHolder(view)
    }

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        val item = items[position]
        holder.itemTitleTextView.text = item.title

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class StackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitleTextView: TextView = itemView.findViewById(R.id.itemTitleTextView)
    }
}

