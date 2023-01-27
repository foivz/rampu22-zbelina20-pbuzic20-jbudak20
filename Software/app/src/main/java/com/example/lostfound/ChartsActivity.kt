package com.example.lostfound

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lostfound.entities.Post
import com.example.lostfound.entities.PostSync
import com.example.lostfound.helpers.FirebaseService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.concurrent.TimeUnit

class ChartsActivity : AppCompatActivity(), PostSync {

    private var postsList : MutableList<Post> = mutableListOf()
    private lateinit var barChart: BarChart
    private lateinit var pieChart : PieChart

    override fun getAllPosts(posts: MutableList<Post>) {
        postsList = posts
        barChart = findViewById(R.id.barchart)
        pieChart = findViewById(R.id.piechart)
        setBarChartValuesForLast30Days()
        setPieChartValuesForLastYear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charts)

        FirebaseService.getAllPosts(this)
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        finish()
    }

    private fun setPieChartValuesForLastYear() {
        val lostItems : List<PieEntry> = getItemsDataForPieChart(365)

        val pieDataSet = PieDataSet(lostItems, "Izgubljeni predmeti")
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        pieDataSet.valueTextSize = 15f
        pieDataSet.valueTextColor = Color.BLACK
        val  pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.animateY(2000)
    }

    private fun setBarChartValuesForLast30Days() {

        val lostItems : List<BarEntry> = getItemsDataForBarChart(30)

        // Create the BarDataSet
        val barDataSet = BarDataSet(lostItems, "Izgubljeni predmeti")
        barDataSet.colors = listOf(Color.RED, Color.BLUE, Color.GREEN)

        barDataSet.valueTextSize = 12f
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setDrawValues(true)

        // Create the data for the chart
        //val barData = BarData(barDataSet)

        // Set the data for the chart
        barChart.data = BarData(barDataSet)
        barChart.description.isEnabled = false

        barChart.setTouchEnabled(true)
        barChart.setDrawValueAboveBar(true)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(false)
        barChart.legend.isEnabled = true
        barChart.legend.textSize = 12f
        barChart.legend.textColor = Color.BLACK
        barChart.invalidate()

        // Animate the chart
        barChart.animateY(1000)

    }

    private fun calculateDaysSincePostCreated(timeInMS : Long) : Int {
        Log.w("FUNKCIJA", (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toDays(timeInMS)).toInt().toString())
        val result : Int = (TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) - TimeUnit.MILLISECONDS.toDays(timeInMS)).toInt()
        return result
    }

    private fun getItemsDataForBarChart(days : Int) : List<BarEntry> {
        var tehnologija = 0
        var razno = 0
        var odjeca = 0

        postsList.forEach {post ->

            val postTime = calculateDaysSincePostCreated(post.creationTimeMs)
            if((post.status == "Izgubljeno" || post.status == "izgubljeno") && (postTime) < days) {

                if(post.vrstaImovine == "Tehnologija") tehnologija++
                else if (post.vrstaImovine == "Razno") razno++
                else odjeca++

            }
        }

        return listOf(BarEntry(1f, tehnologija.toFloat(), "Tehnologija"), BarEntry(2f, odjeca.toFloat(), "Odjeca i obuca"), BarEntry(3f, razno.toFloat(), "Razno"))
    }

    private fun getItemsDataForPieChart(days : Int) : List<PieEntry> {
        var tehnologija = 0
        var razno = 0
        var odjeca = 0

        postsList.forEach {post ->

            val postTime = calculateDaysSincePostCreated(post.creationTimeMs)
            if((post.status == "Izgubljeno" || post.status == "izgubljeno") && (postTime) < days) {

                if(post.vrstaImovine == "Tehnologija") tehnologija++
                else if (post.vrstaImovine == "Razno") razno++
                else odjeca++

            }
        }

        return listOf(PieEntry(tehnologija.toFloat(), "Tehnologija"), PieEntry(odjeca.toFloat(), "Odjeca i obuca"), PieEntry(razno.toFloat(), "Razno"))
    }
}