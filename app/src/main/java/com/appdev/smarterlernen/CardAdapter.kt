package com.appdev.smarterlernen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.interfaces.StackDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardAdapter(private var items: List<Card>, private val context: Context, private val onItemClick: (Card) -> Unit, private val onEditClick: (Card) -> Unit) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
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
   if (item.rating ==0){
       holder.tvRating.text="no rating"
   } else if (item.rating== 1) {
       holder.tvRating.text= "Leicht"
   }else if (item.rating== 2) {
       holder.tvRating.text= "Mittel"
   }else if (item.rating== 3) {
       holder.tvRating.text= "Schwer"
   }
        items =items.sortedByDescending { it.rating }
        holder.buttonShowBack.setOnClickListener {
            onItemClick(item)
        }

        holder.editBtn.setOnClickListener {
            onEditClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
     fun updateData(items2: List<Card>) {
         items =items2
         notifyDataSetChanged()

    }


    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonShowBack: Button = itemView.findViewById(R.id.buttonShowBack)
        val tVTitle: TextView = itemView.findViewById(R.id.textView2)
        val cardId: TextView = itemView.findViewById(R.id.cardId)
        val tVContent: TextView = itemView.findViewById(R.id.tVContent)
        val tvRating: TextView = itemView.findViewById(R.id.rating)
        val editBtn: ImageButton = itemView.findViewById(R.id.editBtn)
    }
}

