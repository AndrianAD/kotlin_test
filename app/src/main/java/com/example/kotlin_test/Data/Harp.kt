package com.example.kotlin_test.Data

import android.widget.TextView
import com.example.kotlin_test.Util
import com.example.kotlin_test.Util.COUNTRY
import com.example.kotlin_test.Util.MINOR
import com.example.kotlin_test.Util.PADDY
import com.example.kotlin_test.Util.RIHTER
import java.util.*


class Harp(var tuning: Int = RIHTER, var position: Int = 5) {

    var keyOfHarp: String = ""
    var allnote = arrayListOf<Pair<String, String>>()
    private val TAG = javaClass.simpleName
    var tempRandom: String? = null

    init {
        allnote = makeAllNotes(tuning, position)
        keyOfHarp = allnote[0].first
    }


//----------------------------------------------------

    fun randomString(): String {
        return if (tempRandom == null) {
            val random: String = Random().nextInt(9).toString()
            val random2: String = Random().nextInt(9).toString()
            val random3: String = Random().nextInt(9).toString()
            val random4: String = Random().nextInt(9).toString()
            tempRandom = random + random2 + random3 + random4
            tempRandom as String

        } else tempRandom as String
    }


    fun makeharp(stroi: Int, position: Int, textView: TextView) {
        allnote = makeAllNotes(stroi, position)
        keyOfHarp = allnote.get(0).first
        this.tuning = stroi
        this.position = position
        print(textView)
    }

    fun print(textView: TextView) {
        var result: String = ""
        for ((index, element) in allnote.withIndex()) {
            result += "$index: $element    "
        }
        textView.setText(result)
    }

    private fun makeAllNotes(stroi: Int, position: Int): ArrayList<Pair<String, String>> {
        val tempList = makeRihterNotes(position)
        return when (stroi) {
            RIHTER -> tempList
            PADDY -> makePaddyNotes(tempList)
            COUNTRY -> makeCountryNotes(tempList)
            MINOR -> makeMinorNotes(tempList)
            else -> throw IllegalArgumentException("Wrong tuning $stroi")
        }
    }


    private fun makeCountryNotes(tempList: ArrayList<Pair<String, String>>): ArrayList<Pair<String, String>> {
        tempList[17] = Pair(tempList[17].first, "-5'")
        tempList[18] = Pair(tempList[18].first, "-5")
        //tempList.set(18,Pair(tempList[18].first, "-5"))
        return tempList
    }


    private fun makePaddyNotes(tempList: ArrayList<Pair<String, String>>): ArrayList<Pair<String, String>> {
        tempList[8] = Pair(tempList[8].first, "2*")
        tempList[9] = Pair(tempList[9].first, "+3")
        return tempList
    }


    private fun makeMinorNotes(tempList: ArrayList<Pair<String, String>>): ArrayList<Pair<String, String>> {
        tempList[3] = Pair(tempList[3].first, "+2")
        tempList[4] = Pair(tempList[4].first, "-2'''")
        tempList[8] = Pair(tempList[8].first, "-3''")
        tempList[9] = Pair(tempList[9].first, "-3'")
        tempList[10] = Pair(tempList[10].first, "-3")
        tempList[11] = Pair(tempList[11].first, "3*")
        tempList[15] = Pair(tempList[15].first, "+5")
        tempList[16] = Pair(tempList[16].first, "-5'")
        tempList[22] = Pair(tempList[22].first, "-7")
        tempList[23] = Pair(tempList[23].first, "+7'")
        tempList[27] = Pair(tempList[27].first, "+8")
        tempList[28] = Pair(tempList[28].first, "-8*")
        return tempList
    }

    private fun makeRihterNotes(position: Int = 5): ArrayList<Pair<String, String>> {
        val resultlist = arrayListOf<Pair<String, String>>()
        var index = position
        for (i in 0 until 38) {
            if (index >= Util.NOTES.size) {
                index = 0
            }
            resultlist.add(Pair(Util.NOTES[index], Util.TABS[i]))
            index++
        }
        return resultlist
    }


    fun splitAllNotesToList(list: ArrayList<Pair<String, String>>, tabsOrNote: Boolean): MutableList<String> {
        var temp = ""
        for (element in list)
            temp += if (tabsOrNote) element.first + " " else element.second + " "
        return temp.split(" ").toMutableList()
    }

}