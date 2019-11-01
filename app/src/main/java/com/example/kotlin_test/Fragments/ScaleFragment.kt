package com.example.kotlin_test.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlin_test.R
import com.example.kotlin_test.SecondActivity.Companion.harp1
import com.example.kotlin_test.SecondActivity.Companion.harp2
import com.example.kotlin_test.Util
import kotlinx.android.synthetic.main.fragment_scale.*
import kotlinx.android.synthetic.main.fragment_scale.view.*

class ScaleFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_scale, container, false)



        makeScale(true, Util.majorScale, view.gamma_major)
        makeScale(true, Util.minorScale, view.gamma_minor)
        makeScale(true, Util.bluesScale, view.gamma_blues)
        makeScale(true, Util.pentaminorScale, view.penta_minor)
        makeScale(true, Util.pentamajorScale, view.penta_major)



        view.checkBox__major.setOnClickListener {

        }

        return view
    }


    fun makeScale(isChecked: Boolean, scaleArray: List<Int>, textView: TextView) {
        val differencePosition = Util.checkDifferencePosition(harp1.position, harp2.position)
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
            result += if (isChecked) nots.first + " " else nots.second + " "
            i += index

        }
        textView.text = result

    }


}
