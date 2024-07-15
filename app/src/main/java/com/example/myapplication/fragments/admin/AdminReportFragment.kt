package com.example.myapplication.fragments.admin

import android.view.LayoutInflater
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.firestore.FirebaseFirestore
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAdminEditTicketBinding
import com.example.myapplication.interfaces.OnClickListener

class AdminReportFragment : Fragment() {

    // on below line we are creating
    // variables for our pie chart
    private lateinit var pieChart: PieChart
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentAdminEditTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminEditTicketBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        return binding.root
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            pieChart = binding.root.findViewById(R.id.pieChart)
        pieChart = binding.root.findViewById(R.id.pieChart)
        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        pieChart.setUsePercentValues(true)
            pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
            pieChart.dragDecelerationFrictionCoef = 0.95f

        // on below line we are setting hole
        // and hole color for pie chart
            pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
            pieChart.holeRadius = 58f
            pieChart.transparentCircleRadius = 61f

        // on below line we are setting center text
        pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
            pieChart.rotationAngle = 0f

        // enable rotation of the pieChart by touch
            pieChart.isRotationEnabled = true
            pieChart.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(70f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(10f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(), R.color.lavander))
        colors.add(ContextCompat.getColor(requireContext(), R.color.lavander2))
        colors.add(ContextCompat.getColor(requireContext(), R.color.lavander3))

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
            pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()

    }
}

