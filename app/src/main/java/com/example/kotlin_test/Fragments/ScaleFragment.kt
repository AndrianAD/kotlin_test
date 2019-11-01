package com.example.kotlin_test.Fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlin_test.R
import com.example.kotlin_test.SecondActivity.Companion.harp1
import com.example.kotlin_test.Util
import com.example.kotlin_test.Util.scaleNote
import kotlinx.android.synthetic.main.fragment_scale.view.*

class ScaleFragment : Fragment() {

    var selectedTonicaINT: Int = 0
    var selectedTonicaString: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_scale, container, false)



        makeAllScales(view)


        var adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(activity, R.array.harmonica_key, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.tonica.adapter = adapter
        view.tonica.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                val choose = resources.getStringArray(R.array.harmonica_key)
                selectedTonicaString = choose[selectedItemPosition]
                if (scaleNote.containsKey(selectedTonicaString));
                selectedTonicaINT = scaleNote[selectedTonicaString]!!
                makeAllScales(view)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.checkBox__major.setOnClickListener {
            val flag = it.checkBox__major.isChecked
            makeScale(!flag, Util.majorScale, view.gamma_major)
        }

        view.checkBox_minor.setOnClickListener {
            val flag = it.checkBox_minor.isChecked
            makeScale(!flag, Util.minorScale, view.gamma_minor)
        }

        view.checkBox_blues.setOnClickListener {
            val flag = it.checkBox_blues.isChecked
            makeScale(!flag, Util.bluesScale, view.gamma_blues)
        }

        view.checkBox_penta_major.setOnClickListener {
            val flag = it.checkBox_penta_major.isChecked
            makeScale(!flag, Util.pentamajorScale, view.penta_major)
        }

        view.checkBox_penta_minor.setOnClickListener {
            val flag = it.checkBox_penta_minor.isChecked
            makeScale(!flag, Util.pentaminorScale, view.penta_minor)
        }

        return view
    }

    private fun makeAllScales(view: View) {
        makeScale(true, Util.majorScale, view.gamma_major)
        makeScale(true, Util.minorScale, view.gamma_minor)
        makeScale(true, Util.bluesScale, view.gamma_blues)
        makeScale(true, Util.pentaminorScale, view.penta_minor)
        makeScale(true, Util.pentamajorScale, view.penta_major)
    }


    fun makeScale(isChecked: Boolean, scaleArray: List<Int>, textView: TextView) {
        val differencePosition = Util.checkDifferencePosition(harp1.position, selectedTonicaINT)
        var i = 0
        var j = 0
        var index: Int
        var nots: Pair<String, String>
        var result = ""
        while (i < 37 - differencePosition) {
            index = scaleArray[j]
            nots = harp1.allnote[i + differencePosition]
            j++
            if (j == scaleArray.size) {
                j = 0
            }
            var temp = if (isChecked) nots.first + " " else nots.second + " "
            result += temp
            i += index
        }

        nots = harp1.allnote[differencePosition]
        val firstTonica = if (isChecked) nots.first + " " else nots.second + " "
        nots = harp1.allnote[differencePosition + 12]
        val secondTonica = if (isChecked) nots.first + " " else nots.second + " "
        nots = harp1.allnote[differencePosition + 24]
        val thirdTonica = if (isChecked) nots.first + " " else nots.second + " "
        val lenght = firstTonica.length
        val lenght2 = secondTonica.length
        val lenght3 = thirdTonica.length
        val indexTonica = if (isChecked) result.indexOf(firstTonica) else result.indexOf(firstTonica)
        val indexTonica2 = if (isChecked) result.indexOf(secondTonica, indexTonica + 1) else result.indexOf(secondTonica)
        val indexTonica3 = if (isChecked) result.indexOf(thirdTonica, indexTonica2 + 1) else result.indexOf(thirdTonica)

        val sb = SpannableStringBuilder(result)
        sb.setSpan(ForegroundColorSpan(Color.RED), indexTonica, indexTonica + lenght, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val sb2 = SpannableStringBuilder(sb)
        sb2.setSpan(ForegroundColorSpan(Color.BLUE), indexTonica2, indexTonica2 + lenght2, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val sb3 = SpannableStringBuilder(sb2)
        sb3.setSpan(ForegroundColorSpan(Color.MAGENTA), indexTonica3, indexTonica3 + lenght3, Spannable.SPAN_INCLUSIVE_INCLUSIVE)


        textView.text = sb3

    }


}
