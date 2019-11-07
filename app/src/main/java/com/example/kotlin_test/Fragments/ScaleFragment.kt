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
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_test.R
import com.example.kotlin_test.SecondActivity.Companion.harp1
import com.example.kotlin_test.SecondActivity.Companion.showHarmonica
import com.example.kotlin_test.Util
import com.example.kotlin_test.Util.scaleNote
import kotlinx.android.synthetic.main.fragment_scale.view.*

class ScaleFragment : Fragment() {

    var selectedTonicaINT: Int = 5
    var selectedTonicaString: String = ""

    companion object {
        var makeScales: MutableLiveData<Boolean> = MutableLiveData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_scale, container, false)

        makeAllScales(view)

        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(activity, R.array.harmonica_key, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.tonica.adapter = adapter
        view.tonica.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                val choose = resources.getStringArray(R.array.harmonica_key)
                selectedTonicaString = choose[selectedItemPosition]
                if (scaleNote.containsKey(selectedTonicaString))
                    selectedTonicaINT = scaleNote[selectedTonicaString]!!
                makeAllScales(view)
                harp1.keyOfHarp = selectedTonicaString
                showHarmonica.value = false
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        makeScales.observe(this, androidx.lifecycle.Observer {
            makeAllScales(view)
        })


        view.checkBox__major.setOnClickListener {
            val flag = it.checkBox__major.isChecked
            view.gamma_major.text = makeScale(!flag, Util.majorScale)
        }

        view.checkBox_minor.setOnClickListener {
            val flag = it.checkBox_minor.isChecked
            view.gamma_minor.text = makeScale(!flag, Util.minorScale)
        }

        view.checkBox_blues.setOnClickListener {
            val flag = it.checkBox_blues.isChecked
            view.gamma_blues.text = makeScale(!flag, Util.bluesScale)
        }

        view.checkBox_penta_major.setOnClickListener {
            val flag = it.checkBox_penta_major.isChecked
            view.penta_major.text = makeScale(!flag, Util.pentaminorScale)
        }

        view.checkBox_penta_minor.setOnClickListener {
            val flag = it.checkBox_penta_minor.isChecked
            view.penta_minor.text = makeScale(!flag, Util.pentamajorScale)
        }


        return view
    }

    private fun makeAllScales(view: View) {
        view.gamma_major.text = makeScale(view.checkBox__major.isChecked, Util.majorScale)
        view.gamma_minor.text = makeScale(view.checkBox_penta_minor.isChecked, Util.minorScale)
        view.gamma_blues.text = makeScale(view.checkBox_blues.isChecked, Util.bluesScale)
        view.penta_major.text = makeScale(view.checkBox_penta_major.isChecked, Util.pentamajorScale)
        view.penta_minor.text = makeScale(view.checkBox_penta_minor.isChecked, Util.pentaminorScale)
    }


    private fun makeScale(isChecked: Boolean, scaleArray: List<Int>): SpannableStringBuilder {

        val differencePosition = Util.checkDifferencePosition(harp1.position, selectedTonicaINT)
        var i = differencePosition
        var j = 0
        val allNotes = harp1.allnote
        var index: Int
        var nots: Pair<String, String>
        var result: String
        val stringBuilder = StringBuilder()


        // calculate before first tonic
        while (i > 0) {
            index = scaleArray[j + scaleArray.size - 1]
            if (i - index < 0) {
                break
            }
            nots = allNotes[i - index]
            j--
            stringBuilder.append(if (isChecked) nots.first + " " else nots.second + " ")
            i -= index
        }
        result = stringBuilder.toString()


        // calculate after first tonic
        i = 0
        j = 0
        while (i < 37 - differencePosition) {
            index = scaleArray[j]
            nots = allNotes[i + differencePosition]
            j++
            if (j == scaleArray.size) {
                j = 0
            }
            val temp = if (isChecked) nots.first + " " else nots.second + " "
            result += temp
            i += index
        }

        return spannableStringBuilder(isChecked, allNotes, differencePosition, result)

    }


    private fun spannableStringBuilder(isChecked: Boolean, allNotes: ArrayList<Pair<String, String>>, differencePosition: Int, result: String): SpannableStringBuilder {
        val firstTonica = if (isChecked) allNotes[differencePosition].first + " " else allNotes[differencePosition].second + " "
        val secondTonica = if (isChecked) allNotes[differencePosition + 12].first + " " else allNotes[differencePosition + 12].second + " "
        val thirdTonica = if (isChecked) allNotes[differencePosition + 24].first + " " else allNotes[differencePosition + 24].second + " "
        val indexTonica1 = if (isChecked) result.indexOf(firstTonica) else result.indexOf(firstTonica)
        val indexTonica2 = if (isChecked) result.indexOf(secondTonica, indexTonica1 + 1) else result.indexOf(secondTonica)
        val indexTonica3 = if (isChecked) result.indexOf(thirdTonica, indexTonica2 + 1) else result.indexOf(thirdTonica)

        val sb = SpannableStringBuilder(result)
        sb.setSpan(ForegroundColorSpan(Color.RED), indexTonica1, indexTonica1 + firstTonica.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val sb2 = SpannableStringBuilder(sb)
        sb2.setSpan(ForegroundColorSpan(Color.BLUE), indexTonica2, indexTonica2 + secondTonica.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val sb3 = SpannableStringBuilder(sb2)
        sb3.setSpan(ForegroundColorSpan(Color.MAGENTA), indexTonica3, indexTonica3 + thirdTonica.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        return sb3
    }


}
