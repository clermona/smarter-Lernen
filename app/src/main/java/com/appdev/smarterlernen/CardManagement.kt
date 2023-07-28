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
        barChart.xAxis.setDrawLabels(false) // Enable the display of x-axis labels
        barChart.xAxis.granularity = 1f

        //right y axis not shown
        barChart.axisRight.isEnabled = false

        //Customize the y Axis
        val yAxisLeft = barChart.axisLeft
        yAxisLeft.axisMinimum = 0f // Minimum value on Y-axis
        yAxisLeft.granularity = 1f // Step size between axis values
        yAxisLeft.textSize = 15f


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
        val easyDataSet = createBarDataSet(entriesEasy, Color.rgb(51, 153, 51), "Leicht")
        val mediumDataSet = createBarDataSet(entriesMedium, Color.rgb(51, 204, 204), "Mittel")
        val hardDataSet = createBarDataSet(entriesHard, Color.rgb(204, 0, 0), "Schwer")

        //change fontsize of the amount of cards per bar
        easyDataSet.setValueTextSize(12f)
        mediumDataSet.setValueTextSize(12f)
        hardDataSet.setValueTextSize(12f)

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
