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

class CardManagement(private val cards: List<Card>) : Fragment() {

    //variablen fuer database
    lateinit var database:AppDatabase
    lateinit var cardDao: CardDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_management, container, false)

        val barChart: BarChart = rootView.findViewById(R.id.barChart)

        // Create example data points
        val entries = mutableListOf<BarEntry>()

        database= AppDatabase.getInstance(requireContext())
        cardDao =database.cardDao()

        val rating = cardDao.getById(1).rating
        val ratingFloat : Float = rating.toFloat()

        for ((index, card) in cards.withIndex()) {
            entries.add(BarEntry(index.toFloat(), card.rating.toFloat()))
        }

//        val stackId = intent.getIntExtra("stack_id", 0)
//
//        runBlocking {
//            launch(Dispatchers.Default) {
//                stack = stackDao.getById(stackId)
//                cardList = cardDao.getByStackId(stackId)
//            }
//        }
//

//
//        entries.add(BarEntry((ratingFloat, 1f))  // Bar 1
//        entries.add(BarEntry(1f, 35f))  // Bar 2
//        entries.add(BarEntry(2f, 10f))  // Bar 3

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
}
