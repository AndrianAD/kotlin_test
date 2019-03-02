package com.example.kotlin_test

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import com.example.kotlin_test.Data.Harp
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*


class SecondActivity : AppCompatActivity() {
    private var harp = Harp()
    val utilViewModel by lazy { ViewModelProviders.of(this).get(SecondActivityViewModel::class.java) }

    companion object {
        var liveData: MutableLiveData<String> = MutableLiveData()
        var result: String = ""
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
        show_harmonica(positionsGrid, harp)
        observGrid()
        liveData.observe(this, android.arch.lifecycle.Observer {
            output_resultat.text = it
            result = it!!
        })
        OK.setOnClickListener { toast(result) }





    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run { putString("output_resultat", result)
            putString("input_resultat", input_resultat.text.toString())}
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        output_resultat.text=savedInstanceState?.getString("output_resultat")
        input_resultat.text=savedInstanceState?.getString("input_resultat")
    }


    fun show_harmonica(positionsGrid: IntArray, harp: Harp) {
        //var all_Notes = harp.allNotesToString(harp.allnote, false)
        val all_Tabs = harp.allNotesToString(harp.allnote, true)
        var array_of_Notes = all_Tabs.split(" ").toMutableList()
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()
        for (i in positionsGrid.indices) {
            var textView = findViewById<TextView>(positionsGrid[i])
            textView.setText(array_of_Notes[i])
        }
    }

    fun observGrid() {
        var harp1 = Harp()
        var harp2 = Harp(position = 6)
        var elementText = ""
        var gridChild = grid_layout.childCount
        var index = 0

        while (index < gridChild) {
            var element = grid_layout.getChildAt(index) as TextView
            element.setOnClickListener {
                elementText += " " + element.text.toString()
                input_resultat.text = elementText
                liveData.value = Util.getResult(harp1, harp2, elementText)
            }
            index++
        }
    }
}


