package com.example.kotlin_test

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.kotlin_test.Data.Harp
import com.example.kotlin_test.Fragments.Setting_Fragment
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*


class SecondActivity : AppCompatActivity() {
    val utilViewModel by lazy { ViewModelProviders.of(this).get(SecondActivityViewModel::class.java) }
    private val REQUEST_CODE_SETTING: Int = 1

    companion object {
        var liveData: MutableLiveData<String> = MutableLiveData()
        var harp1: MutableLiveData<Harp> = MutableLiveData()
        var harp2: MutableLiveData<Harp> = MutableLiveData()
        var result: String = ""
        var inPutText: String = ""
        var fragment: FragmentManager? = null


    }

    init {
        fragment = supportFragmentManager
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



        harp1.observe(this, android.arch.lifecycle.Observer {
            show_harmonica(positionsGrid, it!!, it!!, harp2.value!!)
        })
        harp2.observe(this, android.arch.lifecycle.Observer {
            show_harmonica(positionsGrid, it!!, harp1.value!!, it!!)
        })








        setting_id.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_fragment, Setting_Fragment())
                    .addToBackStack("Back")
                    .commit()
            setting_id.visibility = View.INVISIBLE
        }


    }



    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putString("output_resultat", result)
            putString("input_resultat", input_resultat.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        output_resultat.text = savedInstanceState?.getString("output_resultat")
        input_resultat.text = savedInstanceState?.getString("input_resultat")
        result = savedInstanceState?.getString("output_resultat")!!
        inPutText = savedInstanceState?.getString("input_resultat")!!
    }


    fun show_harmonica(positionsGrid: IntArray, harp: Harp, harp1: Harp, harp2: Harp) {

        var all_Notes = harp.allNotesToString(harp.allnote, false)
        //val all_Tabs = harp.allNotesToString(harp.allnote, true)
        var array_of_Notes = all_Notes.split(" ").toMutableList()
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()

        var hole = findViewById(R.id.b12) as TextView
        hole.setText("+3")

        var listOfActiveGrid: MutableList<TextView> = mutableListOf()

        for (i in positionsGrid.indices) {
            hole = findViewById<TextView>(positionsGrid[i])
            hole.setText(array_of_Notes[i])
            hole.isClickable = true
            hole.addRipple()
            listOfActiveGrid.add(hole)
        }
        var index = 0
        while (index < listOfActiveGrid.size) {
            var element = listOfActiveGrid[index]
            element.setOnClickListener {
                inPutText += " " + element.text.toString()
                input_resultat.text = inPutText
                liveData.value = Util.getResult(harp1, harp2, inPutText)
                liveData.observe(this, android.arch.lifecycle.Observer {
                    output_resultat.text = it
                    result = it!!
                })
            }
            index++
        }
    }

}


