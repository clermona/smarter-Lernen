package com.gtappdevelopers.kotlingfgproject

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.appdev.smarterlernen.R
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.entities.Stack
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardManagement : Fragment() {

    // on below line we are creating
    // variables for our bar chart
    lateinit var barChart: BarChart

    // on below line we are creating
    // a variable for bar data
    lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    //variablen fuer database
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialisieren der Datenbank
        database = AppDatabase.getInstance(requireContext())
        cardDao = database.cardDao()

        // on below line we are initializing
        // our variable with their ids.
       // barChart = findViewById(R.id.idBarChart)
        val barChart = view?.findViewById<BarChart>(R.id.idBarChart)

        // on below line we are calling get bar
        // chart data to add data to our array list
        getBarChartData()

        // on below line we are initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")

        // on below line we are initializing our bar data
        barData = BarData(barDataSet)

        // on below line we are setting data to our bar chart
        if (barChart != null) {
            barChart.data = barData
        }

        // on below line we are setting colors for our bar chart text
        barDataSet.valueTextColor = Color.BLACK

        // on below line we are setting color for our bar data set
        barDataSet.setColor(resources.getColor(R.color.pink))

        // on below line we are setting text size
        barDataSet.valueTextSize = 16f

        // on below line we are enabling description as false
        if (barChart != null) {
            barChart.description.isEnabled = false
        }

    }

    private fun getBarChartData() {

    }

    //runBlocking unnd launch brauch ich immer bei der Datenbank
//        if(title != null) {
//            runBlocking {
//                launch(Dispatchers.Default) {
//                    if(stackDao.getByTitle(title)  != null) {
//                        stackDao.update(Stack(title))
//                    } else {
//                        stackDao.insert(Stack(title))
//                    }
//                }
//
//    }

}
