import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appdev.smarterlernen.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class CardManagement : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_management, container, false)

        val barChart: BarChart = rootView.findViewById(R.id.barChart)

        // Create example data points
        val entries = mutableListOf<BarEntry>()
        entries.add(BarEntry(0f, 20f))  // Bar 1
        entries.add(BarEntry(1f, 35f))  // Bar 2
        entries.add(BarEntry(2f, 10f))  // Bar 3

        // Customize the BarDataSet
        val dataSet = BarDataSet(entries, "Data") // "Data" is the label for the dataset
        dataSet.color = Color.MAGENTA // Set the color of the bars

        // Create BarData and assign the BarDataSet to it
        val data = BarData(dataSet)

        // Customize the appearance of the chart
        barChart.setFitBars(true) // Make the bars fit the available space
        barChart.description.isEnabled = false // Disable the chart description
        barChart.data = data // Assign the BarData to the chart

        // Refresh the chart to display the data
        barChart.invalidate()

        return rootView
    }
}
