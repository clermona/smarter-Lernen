//import android.graphics.Color
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.appdev.smarterlernen.R
//import com.appdev.smarterlernen.database.AppDatabase
//import com.appdev.smarterlernen.database.entities.Card
//import com.appdev.smarterlernen.database.interfaces.CardDao
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//
//class CardManagement : Fragment() {
//
//    // Variablen fuer database
//    lateinit var database: AppDatabase
//    lateinit var cardDao: CardDao
//    lateinit var cardList: List<Card>
//    val entries = mutableListOf<BarEntry>()
//    var countEasyCards = 0
//    var countMediumCards = 0
//    var countHardCards = 0
//    lateinit var barChart: BarChart
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_card_management, container, false)
//
//        barChart = rootView.findViewById(R.id.barChart)
//
//        database = AppDatabase.getInstance(requireContext())
//        cardDao = database.cardDao()
//
//        // Load the initial countEasyCards value and add the entry to the list
//        runBlocking {
//            launch(Dispatchers.Default) {
//                countEasyCards = cardDao.getCountEasyCards()
//                countMediumCards = cardDao.getCountMediumCards();
//                countHardCards = cardDao.getCountHardCards()
//
//                entries.clear()
//                entries.add(BarEntry(0f, countEasyCards.toFloat()))
//                entries.add(BarEntry(1f, countMediumCards.toFloat()))
//                entries.add(BarEntry(2f, countMediumCards.toFloat()))
//                updateChartData()
//            }
//        }
//
//        // Customize the BarDataSet
//        val dataSet = BarDataSet(entries, "leicht") // "Data" is the label for the dataset
//        dataSet.color = Color.rgb(48,103,84) // Set the color of the bars
//
//        // Create BarData and assign the BarDataSet to it
//        val data = BarData(dataSet)
//
//        val xAxis = barChart.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM // Set the position of the x-axis
//        xAxis.setDrawLabels(true) // Enable the display of x-axis labels
//        xAxis.textColor = Color.BLACK // Set the color of the x-axis labels
//        xAxis.textSize = 12f // Set the text size of the x-axis labels
//        xAxis.granularity = 1f
//
//        // Customize the appearance of the chart
//        barChart.setFitBars(true) // Make the bars fit the available space
//        barChart.description.isEnabled = false // Disable the chart description
//        barChart.data = data // Assign the BarData to the chart
//
//        // Refresh the chart to display the data
//        barChart.invalidate()
//
//        return rootView
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        runBlocking {
//            launch(Dispatchers.Default) {
//                countEasyCards = cardDao.getCountEasyCards()
//                entries.clear()
//                entries.add(BarEntry(0f, countEasyCards.toFloat()))
//                updateChartData()
//            }
//        }
//
//        barChart.invalidate()
//    }
//
//    private fun updateChartData() {
//        // Check if barChart.data is not null
//        val barData = barChart.data ?: BarData()
//        // Check if the BarDataSet exists or create a new one
//        val dataSet: BarDataSet = if (barData.dataSetCount > 0) {
//            barData.getDataSetByIndex(0) as BarDataSet
//        } else {
//            BarDataSet(entries, "Schwierigkeit")
//        }
//        dataSet.values = entries
//        dataSet.color = Color.GREEN // Set the color of the bars
//        // Add the dataset to the BarData
//        barData.addDataSet(dataSet)
//        // Set the BarData to the chart
//        barChart.data = barData
//        // Refresh the chart to display the updated data
//        barChart.invalidate()
//    }
//}
//
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appdev.smarterlernen.R
import com.appdev.smarterlernen.database.AppDatabase
import com.appdev.smarterlernen.database.entities.Card
import com.appdev.smarterlernen.database.interfaces.CardDao
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CardManagement : Fragment() {

    // Variables for database
    lateinit var database: AppDatabase
    lateinit var cardDao: CardDao
    lateinit var cardList: List<Card>
    val entriesEasy = mutableListOf<BarEntry>()
    val entriesMedium = mutableListOf<BarEntry>()
    val entriesHard = mutableListOf<BarEntry>()
    lateinit var barChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_management, container, false)

        barChart = rootView.findViewById(R.id.barChart)

        database = AppDatabase.getInstance(requireContext())
        cardDao = database.cardDao()

        // Load the initial countEasyCards value and add the entry to the list
        runBlocking {
            launch(Dispatchers.Default) {
                val easyCards = cardDao.getCountEasyCards()
                val mediumCards = cardDao.getCountMediumCards()
                val hardCards = cardDao.getCountHardCards()

                // Clear existing entries and add new data points for each card type
                entriesEasy.clear()
                entriesEasy.add(BarEntry(0f, easyCards.toFloat()))

                entriesMedium.clear()
                entriesMedium.add(BarEntry(1f, mediumCards.toFloat()))

                entriesHard.clear()
                entriesHard.add(BarEntry(2f, hardCards.toFloat()))

                updateChartData()
            }
        }

        // Customize the appearance of the chart
        barChart.setFitBars(true) // Make the bars fit the available space
        barChart.description.isEnabled = false // Disable the chart description
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM // Set the position of the x-axis
        barChart.xAxis.setDrawLabels(true) // Enable the display of x-axis labels
        barChart.xAxis.textColor = Color.BLACK // Set the color of the x-axis labels
        barChart.xAxis.textSize = 12f // Set the text size of the x-axis labels
        barChart.xAxis.granularity = 1f

        // Refresh the chart to display the data
        barChart.invalidate()

        return rootView
    }

    override fun onResume() {
        super.onResume()

        runBlocking {
            launch(Dispatchers.Default) {
                val easyCards = cardDao.getCountEasyCards()
                val mediumCards = cardDao.getCountMediumCards()
                val hardCards = cardDao.getCountHardCards()

                // Clear existing entries and add new data points for each card type
                entriesEasy.clear()
                entriesEasy.add(BarEntry(0f, easyCards.toFloat()))

                entriesMedium.clear()
                entriesMedium.add(BarEntry(1f, mediumCards.toFloat()))

                entriesHard.clear()
                entriesHard.add(BarEntry(2f, hardCards.toFloat()))

                updateChartData()
            }
        }

        barChart.invalidate()
    }

    private fun updateChartData() {
        val barData = BarData()

        // Create BarDataSet objects for each type of card.
        val easyDataSet = createBarDataSet(entriesEasy, Color.GREEN, "Leicht")
        val mediumDataSet = createBarDataSet(entriesMedium, Color.BLUE, "Mittel")
        val hardDataSet = createBarDataSet(entriesHard, Color.RED, "Schwer")

        // Add the BarDataSet objects to BarData.
        barData.addDataSet(easyDataSet)
        barData.addDataSet(mediumDataSet)
        barData.addDataSet(hardDataSet)

        // Set the BarData to the chart
        barChart.data = barData

        // Refresh the chart to display the updated data
        barChart.invalidate()
    }

    private fun createBarDataSet(entries: List<BarEntry>, color: Int, label: String): BarDataSet {
        val dataSet = BarDataSet(entries, label)
        dataSet.color = color
        return dataSet
    }

}
