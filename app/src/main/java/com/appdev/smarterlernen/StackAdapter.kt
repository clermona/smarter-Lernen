package com.appdev.smarterlernen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StackAdapter(private val items: List<Stack>, private val context: Context, private val onItemClick: (Stack) -> Unit) : RecyclerView.Adapter<StackAdapter.StackViewHolder>() {
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao
    var  numberCards:Int =0
   lateinit var  ratedCards:List<Card>
    var  sum :Int =0
    var res:Int =0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return StackViewHolder(view)
    }

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        database = AppDatabase.getInstance(context)
        cardDao = database.cardDao()

        val item = items[position]
        runBlocking {
            launch(Dispatchers.Default) {
                ratedCards=cardDao.getByStackId(item.id)
                 numberCards=  ratedCards.filter { it.rating != 0 }.count()
                 sum=  cardDao.getByStackId(item.id).sumOf{ item -> item.rating }

            }
        }
        if(ratedCards.filter { it.rating==0 }.count()== ratedCards.count())
        holder.tVlevel.text = context.getString(R.string.label_no_rating)
        else {

           item.rating  = sum / numberCards
            var resText = ""

            if ( item.rating < 1.5)
                resText = context.getString(R.string.label_rating_easy)
            if ( item.rating > 1.5 && res < 2.5)
                resText = context.getString(R.string.label_rating_medium)
            if ( item.rating > 2.5)
                resText = context.getString(R.string.label_rating_difficult)

            holder.tVlevel.text = context.getString(R.string.label_rating, resText)
        }
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
        val tVlevel: TextView = itemView.findViewById(R.id.tVLevel)
    }
}

