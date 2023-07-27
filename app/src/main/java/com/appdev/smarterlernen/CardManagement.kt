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

class CardManagement() : Fragment() {

    //variablen fuer database
    lateinit var database:AppDatabase
    lateinit var cardDao: CardDao
    lateinit var cardList : List<Card>
    val entries = mutableListOf<BarEntry>()
    var countEasyCards = 0
    lateinit var barChart: BarChart

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_management, container, false)

        barChart = rootView.findViewById(R.id.barChart)

        database= AppDatabase.getInstance(requireContext())
        cardDao =database.cardDao()

        runBlocking {
            launch(Dispatchers.Default) {
                countEasyCards = cardDao.getCountEasyCards()
            }
        }

        entries.add(BarEntry(0f, countEasyCards.toFloat()))

        // Customize the BarDataSet
        val dataSet = BarDataSet(entries, "Schwierigkeit") // "Data" is the label for the dataset
        dataSet.color = Color.GREEN // Set the color of the bars

        // Create BarData and assign the BarDataSet to it
        val data = BarData(dataSet)

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM // Set the position of the x-axis
        xAxis.setDrawLabels(true) // Enable the display of x-axis labels
        xAxis.textColor = Color.BLACK // Set the color of the x-axis labels
        xAxis.textSize = 12f // Set the text size of the x-axis labels
        xAxis.granularity = 1f

        // Customize the appearance of the chart
        barChart.setFitBars(true) // Make the bars fit the available space
        barChart.description.isEnabled = false // Disable the chart description
        barChart.data = data // Assign the BarData to the chart

        // Refresh the chart to display the data
        barChart.invalidate()

        return rootView
    }
    override fun onResume() {
        super.onResume()

        runBlocking {
            launch(Dispatchers.Default) {
                countEasyCards = cardDao.getCountEasyCards()
            }
        }

        entries.add(BarEntry(0f, countEasyCards.toFloat()))

        barChart.invalidate()

    }
}
