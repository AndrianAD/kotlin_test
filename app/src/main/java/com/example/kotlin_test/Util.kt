package com.example.kotlin_test

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.widget.Toast

import com.example.kotlin_test.Data.Harp


object Util {


    fun getResult(ourHarp: Harp, selectedHarp: Harp, tabs: String): String {
        return calculate(ourHarp, selectedHarp, tabs)
    }

    fun get_input_tabs(inputtabs: String, harp: Harp): MutableList<String> {
        var str = inputtabs.split(" ").toMutableList()
        for (index in str.indices)
            if (str[index] == "3" && harp.stroi !== "Падди") {
                str[index] = "-2"
            }
        return str
    }


    val TABS = arrayOf("1", "-1'", "-1", "1*", "2", "-2''", "-2'", "-2", "-3'''", "-3''", "-3'", "-3", "4", "-4'",
            "-4", "4*", "5", "-5", "5*", "6", "-6'", "-6", "6*", "-7", "7", "-7*", "-8", "8'", "8", "-9", "9'",
            "9", "-9*", "-10", "10''", "10'", "10", "-10*", "", "", "", "", "", "", "", "", "", "", "")
    val NOTES = arrayOf("G", "Ab", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#")
    val STROI = arrayOf("Rihter", "Paddy", "Country", "Нат. Минор")

    fun calculate(ourHarp: Harp, selectedHarp: Harp, string: String): String {
        if (ourHarp.allnote.isEmpty() || selectedHarp.allnote.isEmpty()) {
            return ""
        }
        var usersTabs = Util.get_input_tabs(string, ourHarp)
        return changetabs(ourHarp, selectedHarp, usersTabs)
    }


    fun changetabs(ourHarp: Harp, selectedHarp: Harp, usersTabs: MutableList<String>): String {
        var rezultat = ""
        val temp = check_difference_position(ourHarp.position, selectedHarp.position)
        for (indexI in usersTabs.indices) {
            if (usersTabs[indexI].contains("\n"))
                rezultat += "\n"
            for (indexJ in ourHarp.allnote.indices) {
                if (usersTabs[indexI] == ourHarp.allnote[indexJ].first) {
                    var newIndexJ = checkMargin(indexJ, temp)
                    rezultat += " " + selectedHarp.allnote[newIndexJ].first
                    break
                }


            }
        }
        return rezultat
    }

    private fun checkMargin(indexJ: Int, temp: Int): Int {
        var sum = indexJ + temp
        if (sum < 0) {
            sum = sum + 12
        }
        if (sum > 37) {
            sum = sum - 12
        }
        return sum
    }


    fun check_difference_position(n: Int, z: Int): Int {
        var temps = z - n
        //val nums = intArrayOf(-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11)
        for (i in -1..-11) {
            if (temps == i) return 12 - -i
        }
        return temps
    }


}



