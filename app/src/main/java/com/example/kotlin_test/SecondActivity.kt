package com.example.kotlin_test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_test.Data.Harp
import kotlinx.android.synthetic.main.activity_second.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*


class SecondActivity : AppCompatActivity() {


    companion object {
        var harp1: MutableLiveData<Harp> = MutableLiveData()
        var harp2: MutableLiveData<Harp> = MutableLiveData()
        var tabsOrNotes: Boolean = true
        lateinit var viewModel: ViewModel
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

        viewModel = getViewModel()


        var navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)


        harp1.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, it, harp2.value!!, tabsOrNotes)
            set3Hole(tabsOrNotes)
        })
        harp2.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, harp1.value!!, it, tabsOrNotes)
            set3Hole(tabsOrNotes)
        })


        viewModel.result.observe(this, androidx.lifecycle.Observer {
            output_resultat.text = it

        })

        viewModel.inPutText.observe(this, androidx.lifecycle.Observer {
            input_resultat.text = it
        })


        setting_id.setOnClickListener {
            tabsOrNotes = !tabsOrNotes
            showHarmonica(positionsGrid, harp1.value!!, harp1.value!!, harp2.value!!, tabsOrNotes)
            set3Hole(tabsOrNotes)
        }

    }

    private fun set3Hole(tabsOrNotes: Boolean) {
        var hole = findViewById<TextView>(R.id.b12)
        if (tabsOrNotes) {
            hole.text = harp1.value!!.allnote[7].first

        } else {
            hole.text = "+3"
        }
    }

    private fun showHarmonica(positionsGrid: IntArray, harp: Harp, harp1: Harp, harp2: Harp, tabsOrNotes: Boolean) {

        var arrayOfNotes = harp.splitAllNotesToList(harp.allnote, tabsOrNotes)
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()
        var hole: TextView
        var listOfActiveGrid: MutableList<TextView> = mutableListOf()

        for (i in positionsGrid.indices) {
            hole = findViewById(positionsGrid[i])
            hole.text = arrayOfNotes[i]
            hole.isClickable = true
            hole.makeSelectable()
            listOfActiveGrid.add(hole)
        }

        for (element in listOfActiveGrid) {
            element.setOnClickListener {
                if (tabsOrNotes.not()) {
                    viewModel.inPutText.value += " " + element.text.toString()
                    viewModel.result.value = Util.getResult(harp1, harp2, viewModel.inPutText.value!!)
                }
            }
        }
    }

    private fun clearView() {
        var hole: TextView
        for (i in positionsGrid.indices) {
            hole = findViewById(positionsGrid[i])
            hole.text = ""
        }
    }


    override fun onBackPressed() {
    }
}


