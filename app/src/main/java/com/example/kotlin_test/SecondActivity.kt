package com.example.kotlin_test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kotlin_test.Data.Harp
import com.example.kotlin_test.Fragments.ScaleFragment.Companion.makeScales
import com.example.kotlin_test.Util.RIHTER
import kotlinx.android.synthetic.main.activity_second.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class SecondActivity : AppCompatActivity(), TextWatcher {

    companion object {
        lateinit var harp1: Harp
        lateinit var harp2: Harp
        var showHarmonica: MutableLiveData<Boolean> = MutableLiveData()
        var tabsOrNotes: Boolean = true
        lateinit var viewModel: ViewModel
        lateinit var positionsGrid: IntArray
        var tune = RIHTER
    }

    init {
        harp1 = Harp()
        harp2 = Harp()
        positionsGrid = GridPosition.getSroi(RIHTER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


//        val newValue = "NEW VALUE"
//        val oldText = "текст песни sdsfdf  dfg  @A#@ ff  sdgdf  gdfdgfh    @Bb@ gf  df   @A@  A Ab A @A " //    @TAG@
//        val regex = "<(\\S+)>".toRegex()
//        val newText = regex.replace(oldText) { oldValue -> newValue } // тут меняем ноту на нужную
//        Log.d("xxx", oldText)
//        Log.d("xxx", newText)


        //Close keyboard on click
        Util.setupUI(window.decorView.rootView, this)
        // ViewModel
        viewModel = getViewModel()

        //Show Harmonica
        showHarmonica(positionsGrid, harp1, tabsOrNotes)
        set3Hole(tabsOrNotes, harp1)

        // NavController
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)

        // TextWatcher
        inputResult.addTextChangedListener(this)

        //----------------------------------------------------
        showHarmonica.observe(this, androidx.lifecycle.Observer {
            showHarmonica(positionsGrid, harp1, tabsOrNotes)
            set3Hole(tabsOrNotes, harp1)
        })

        viewModel.result.observe(this, androidx.lifecycle.Observer { outputResult.text = it })
        viewModel.inPutText.observe(this, androidx.lifecycle.Observer { inputResult.setText(it) })


        setting_id.setOnClickListener {
            Util.clearView2(grid_layout)
            tabsOrNotes = !tabsOrNotes
            showHarmonica(positionsGrid, harp1, tabsOrNotes)
            set3Hole(tabsOrNotes, harp1)
        }

        backspace.setOnClickListener {
            viewModel.result.value = outputResult.text.dropLastWhile { it != ' ' }.dropLast(1).toString()
            viewModel.inPutText.value = inputResult.text.dropLastWhile { it != ' ' }.dropLast(1).toString()
        }


        seekBarKey.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {

                if (tabsOrNotes) {
                    harp1 = Harp(position = progress)
                    textKey.text = harp1.keyOfHarp
                    showHarmonica.value = true
                    viewModel.result.value = Util.getResult(harp1, harp2, inputResult.text.toString())
                } else {
                    harp1 = Harp(position = progress)
                    textKey.text = harp1.keyOfHarp
                    viewModel.result.value = Util.getResult(harp1, harp2, inputResult.text.toString())
                }
                makeScales.value = true
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        seekBarStroi.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                Util.clearView(this@SecondActivity)
                positionsGrid = GridPosition.getSroi(progress)
                tune = progress
                textStroi.text = Util.STROI[progress]
                harp1 = Harp(tuning = progress, position = harp1.position)
                showHarmonica.value = true
                viewModel.result.value = Util.getResult(harp1, harp2, inputResult.text.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


    }

    private fun set3Hole(tabsOrNotes: Boolean, harp: Harp) {
        val hole = findViewById<TextView>(R.id.b12)
        if (tabsOrNotes) {
            hole.text = harp.allnote[7].first
        } else hole.text = "+3"
    }

    private fun showHarmonica(positionsGrid: IntArray, harp: Harp, tabsOrNotes: Boolean) {

        val arrayOfNotes = harp.splitAllNotesToList(harp.allnote, tabsOrNotes)
        //  val array_of_allTabs =all_Tabs.split(" ").toMutableList()
        var hole: TextView
        val listOfActiveGrid: MutableList<TextView> = mutableListOf()

        for (i in positionsGrid.indices) {
            hole = findViewById(positionsGrid[i])
            hole.text = arrayOfNotes[i]
            //hole.isClickable = true
            hole.makeSelectable()
            listOfActiveGrid.add(hole)
        }

        val hole3 = findViewById<TextView>(R.id.b12)
        hole3.setOnClickListener {
            viewModel.inPutText.value += " " + "<+3>"
        }

        for ((index, element) in listOfActiveGrid.withIndex()) {
            element.setOnClickListener {
                viewModel.inPutText.value = inputResult.text.toString() + " " + "<" + harp.allnote[index].second + ">"
                inputResult.setSelection(inputResult.text.length)
            }
        }
    }


    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Main Calculation!!!
        viewModel.result.value = Util.getResult(harp1, harp2, s.toString())
    }

    override fun onBackPressed() {}

}


