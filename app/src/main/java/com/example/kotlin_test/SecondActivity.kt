package com.example.kotlin_test

import androidx.lifecycle.MutableLiveData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_test.Data.Harp
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*


class SecondActivity : AppCompatActivity() {


    companion object {
        var result: MutableLiveData<String> = MutableLiveData()
        var harp1: MutableLiveData<Harp> = MutableLiveData()
        var harp2: MutableLiveData<Harp> = MutableLiveData()
        var inPutText: String = ""
        var tabsOrNotes: Boolean = true
    }

    init {
        harp1.value = Harp()
        harp2.value = Harp()
    }


    private var positionsGrid = GridPosition.getRihter()

    private val scaleNote = object : HashMap<String, Int>() {
        init {
            put("G", 0)
            put("Ab", 1)
            put("A", 2)
            put("Bb", 3)
            put("B", 4)
            put("C", 5)
            put("C#", 6)
            put("D", 7)
            put("Eb", 8)
            put("E", 9)
            put("F", 10)
            put("F#", 11)
        }
    }
    private val majorScale = intArrayOf(2, 2, 1, 2, 2, 2, 1)
    private val minorScale = intArrayOf(2, 1, 2, 2, 1, 2, 2)
    private val bluesScale = intArrayOf(3, 2, 1, 1, 3, 2)
    private val pentamajorScale = intArrayOf(2, 2, 3, 2, 3)
    private val pentaminorScale = intArrayOf(3, 2, 2, 3, 2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottom_nav, navController)


        harp1.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, it, harp2.value!!, tabsOrNotes)
        })
        harp2.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, harp1.value!!, it, tabsOrNotes)
        })

        setting_id.setOnClickListener {
            if (tabsOrNotes) tabsOrNotes = false else tabsOrNotes = true
            showHarmonica(positionsGrid, harp1.value!!, harp1.value!!, harp2.value!!, tabsOrNotes)
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
//        output_resultat.text = savedInstanceState?.getString("output_resultat") ?: " "
//        input_resultat.text = savedInstanceState?.getString("input_resultat") ?: " "
//        result.value = savedInstanceState?.getString("output_resultat")
//        inPutText = savedInstanceState?.getString("input_resultat")!!
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState!!)
                outState.run {
//            putString("output_resultat", result.value)
//            putString("input_resultat", input_resultat.text.toString())
        }

    }


    fun showHarmonica(positionsGrid: IntArray, harp: Harp, harp1: Harp, harp2: Harp, tabsOrNotes: Boolean) {

        var array_of_Notes = harp.splitAllNotesToList(harp.allnote, tabsOrNotes)
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()
        var hole = findViewById(R.id.b12) as TextView
//        hole.setText("+3")

        var listOfActiveGrid: MutableList<TextView> = mutableListOf()

        for (i in positionsGrid.indices) {
            hole = findViewById(positionsGrid[i])
            hole.text = array_of_Notes[i]
            hole.isClickable = true
            hole.makeSelectable()
            listOfActiveGrid.add(hole)
        }
        var index = 0
        while (index < listOfActiveGrid.size) {
            val element = listOfActiveGrid[index]
            element.setOnClickListener {
                inPutText += " " + element.text.toString()
                input_resultat.text = inPutText

                result.value = Util.getResult(harp1, harp2, inPutText)
                result.observe(this, androidx.lifecycle.Observer {
                    output_resultat.text = it

                })
            }
            index++
        }
    }

    private fun clearView() {
        var hole: TextView
        for (i in positionsGrid.indices) {
            hole = findViewById<TextView>(positionsGrid[i])
            hole.setText("")
        }
    }


//    fun makeScale(isChecked: Boolean, scale_array: IntArray, resultView: TextView?): String {
//
//
//    }


}


