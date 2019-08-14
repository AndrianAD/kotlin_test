package com.example.kotlin_test.Data

import androidx.lifecycle.*
import android.util.Log
import android.widget.TextView
import com.example.kotlin_test.Util
import java.util.*

class Harp(var stroi: String = "Rihter", var position: Int = 5) : ViewModel(), LifecycleObserver {

    var key_of_harp: String = ""
    var allnote = arrayListOf<Pair<String, String>>()
    private val TAG = javaClass.simpleName
    var tempRandom: String? = null

    init {
        allnote = makeAllNotes(stroi, position)
        key_of_harp = allnote.get(0).first
    }

    //LIFECYCLE
    //------------------------------------
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun OnCreateEvent() {
        Log.i(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun OnStartEvent() {
        Log.i(TAG, "ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun OnStopEvent() {
        Log.i(TAG, "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun OnPauseEvent() {
        Log.i(TAG, "ON_PAUSE")
    }


//----------------------------------------------------

    fun randomString(): String {
        if (tempRandom == null) {
            val random: String = Random().nextInt(9).toString()
            val random2: String = Random().nextInt(9).toString()
            val random3: String = Random().nextInt(9).toString()
            val random4: String = Random().nextInt(9).toString()
            tempRandom = random + random2 + random3 + random4
            return tempRandom as String

        } else return tempRandom as String
    }


    fun makeharp(stroi: String, position: Int, textView: TextView) {
        allnote = makeAllNotes(stroi, position)
        key_of_harp = allnote.get(0).first
        this.stroi = stroi
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

    private fun makeAllNotes(stroi: String, position: Int): ArrayList<Pair<String, String>> {
        var tempList = makeRihterNotes(position)
        when (stroi) {
            "Rihter" -> return tempList
            "Paddy" -> return makePaddyNotes(tempList)
            "Country" -> return makeCountryNotes(tempList)
            "Нат. Минор" -> return makeRihterNotes(position)

            else -> throw IllegalArgumentException("Wrong stroi $stroi")
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
        tempList[9] = Pair(tempList[9].first, "3")
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
            temp+= if(tabsOrNote) element.first+" " else element.second+" "
        return temp.split(" ").toMutableList()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared Метод сработал")

    }

}