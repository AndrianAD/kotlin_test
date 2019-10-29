package com.example.kotlin_test


import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.Toast


import com.example.kotlin_test.Data.Harp


object Util {

    fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, duration).show()
    }

    fun getResult(ourHarp: Harp, selectedHarp: Harp, tabs: String): String {
        return calculate(ourHarp, selectedHarp, tabs)
    }

    private fun getInputTabs(inputtabs: String, harp: Harp): MutableList<String> {
        val str = inputtabs.split(" ").toMutableList()
        for (index in str.indices)
            if (str[index] == "+3" && harp.stroi !== "Падди") {
                str[index] = "-2"
            }
        return str
    }


    val TABS = arrayOf("+1", "-1'", "-1", "1*", "+2", "-2''", "-2'", "-2", "-3'''", "-3''", "-3'", "-3", "+4", "-4'",
            "-4", "4*", "+5", "-5", "5*", "+6", "-6'", "-6", "6*", "-7", "+7", "-7*", "-8", "8'", "+8", "-9", "9'",
            "+9", "-9*", "-10", "10''", "10'", "+10", "-10*", "", "", "", "", "", "", "", "", "", "", "")
    val NOTES = arrayOf("G", "Ab", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#")
    val STROI = arrayOf("Rihter", "Paddy", "Country", "Нат. Минор")

    private fun calculate(ourHarp: Harp, selectedHarp: Harp, string: String): String {
        if (ourHarp.allnote.isEmpty() || selectedHarp.allnote.isEmpty()) {
            return ""
        }
        var usersTabs = Util.getInputTabs(string, ourHarp)
        return changetabs(ourHarp, selectedHarp, usersTabs)
    }


    private fun changetabs(ourHarp: Harp, selectedHarp: Harp, usersTabs: MutableList<String>): String {
        var rezultat = ""
        val temp = checkDifferencePosition(ourHarp.position, selectedHarp.position)
        for (indexI in usersTabs.indices) {
            if (usersTabs[indexI].contains("\n"))
                rezultat += "\n"
            for (indexJ in ourHarp.allnote.indices) {
                if (usersTabs[indexI] == ourHarp.allnote[indexJ].second) {
                    val newIndexJ = checkMargin(indexJ, temp)
                    rezultat += " " + selectedHarp.allnote[newIndexJ].second
                    break
                }
            }
        }
        return rezultat
    }

    private fun checkMargin(indexJ: Int, temp: Int): Int {
        var sum = indexJ + temp
        if (sum < 0) {
            sum += 12
        }
        if (sum > 37) {
            sum -= 12
        }
        return sum
    }


    private fun checkDifferencePosition(n: Int, z: Int): Int {
        val temps = z - n
        for (i in -1 downTo -11) {
            if (temps == i) return 12 - -i
        }
        return temps
    }


}

fun View.makeSelectable() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}



