package com.example.kotlin_test

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_test.Data.Harp
import com.example.kotlin_test.Util.RIHTER
import kotlinx.android.synthetic.main.activity_second.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class SecondActivity : AppCompatActivity(), TextWatcher {
    companion object {
        var harp1: MutableLiveData<Harp> = MutableLiveData()
        var harp2: MutableLiveData<Harp> = MutableLiveData()
        var tabsOrNotes: Boolean = true
        lateinit var viewModel: ViewModel
        lateinit var positionsGrid: IntArray
        var stroi = RIHTER
    }

    init {
        harp1.value = Harp()
        harp2.value = Harp()
        positionsGrid = GridPosition.getSroi(RIHTER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Util.setupUI(window.decorView.rootView, this)
        viewModel = getViewModel()


        var navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)


        harp1.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, it, harp2.value!!, tabsOrNotes)
            set3Hole(tabsOrNotes, harp1)
        })
        harp2.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, it!!, harp1.value!!, it, tabsOrNotes)
            set3Hole(tabsOrNotes, harp2)
        })


        viewModel.result.observe(this, androidx.lifecycle.Observer {
            outputResult.text = it

        })



        viewModel.inPutText.observe(this, androidx.lifecycle.Observer {
            inputResult.setText(it)
        })


        setting_id.setOnClickListener {
            tabsOrNotes = !tabsOrNotes
            harp1.value = Harp(position = harp1.value!!.position, stroi = stroi)
            set3Hole(tabsOrNotes, harp1)
        }

        backspace.setOnClickListener {
            viewModel.result.value = outputResult.text.dropLastWhile { it != ' ' }.dropLast(1).toString()
            viewModel.inPutText.value = inputResult.text.dropLastWhile { it != ' ' }.dropLast(1).toString()
        }


    }

    private fun set3Hole(tabsOrNotes: Boolean, harp: MutableLiveData<Harp>) {
        val hole = findViewById<TextView>(R.id.b12)
        if (tabsOrNotes) {
            hole.text = harp.value!!.allnote[7].first

        } else {
            hole.text = "+3"
        }
    }

    private fun showHarmonica(positionsGrid: IntArray, harp: Harp, harp1: Harp, harp2: Harp, tabsOrNotes: Boolean) {

        val arrayOfNotes = harp.splitAllNotesToList(harp.allnote, tabsOrNotes)
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()
        var hole: TextView
        val listOfActiveGrid: MutableList<TextView> = mutableListOf()

        for (i in positionsGrid.indices) {
            hole = findViewById(positionsGrid[i])
            hole.text = arrayOfNotes[i]
            hole.isClickable = true
            hole.makeSelectable()
            listOfActiveGrid.add(hole)
        }

        inputResult.addTextChangedListener(this)

        var hole3 = findViewById<TextView>(R.id.b12)
        hole3.setOnClickListener {
                viewModel.inPutText.value += " " + "+3"
        }

        for ((index,element) in listOfActiveGrid.withIndex()) {
            element.setOnClickListener {
                    viewModel.inPutText.value += " " + harp.allnote.get(index).second
            }
        }
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.result.value = Util.getResult(harp1.value!!, harp2.value!!, s.toString())
    }

    override fun onBackPressed() {
    }

}


