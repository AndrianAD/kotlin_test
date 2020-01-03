package com.example.kotlin_test


import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.Data.Harp
import java.security.AccessController
import java.util.*


fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

object Util {

    fun setupUI(view: View, context: Context) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                if (AccessController.getContext() != null)
                    hideKeyboard(context)
                false
            }
        }
        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView, context)
            }
        }
    }

    private fun hideKeyboard(context: Context) {
        val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if ((context as Activity).currentFocus != null) {
            try {
                inputManager.hideSoftInputFromWindow(
                        (context as AppCompatActivity).currentFocus!!.windowToken,
                        0
                )
            } catch (ex: NullPointerException) {
                ex.printStackTrace()
            }

        }
    }

    fun getResult(ourHarp: Harp, selectedHarp: Harp, tabs: String): String {
        if (ourHarp.allnote.isEmpty() || selectedHarp.allnote.isEmpty()) {
            return ""
        }
        // Todo delete later
        //val usersTabs = getInputTabs(tabs.replace("\n", " \n "), ourHarp)
        return changeTabs(ourHarp, selectedHarp, tabs)
    }



    // Todo delete later
//    private fun getInputTabs(inputtabs: String, harp: Harp): MutableList<String> {
//        val splited = inputtabs.split(" ").toMutableList()
//        for (index in splited.indices)
//            if (splited[index] == "+3" && harp.tuning != PADDY) {
//                splited[index] = "-2"
//            }
//        return splited
//    }


//    private fun changetabs(ourHarp: Harp, selectedHarp: Harp, usersTabs: MutableList<String>): String {
//        var rezult = ""
//        val temp = checkDifferencePosition(ourHarp.position, selectedHarp.position)
//        for (indexI in usersTabs.indices) {
//            if (usersTabs[indexI].contains("\n"))
//                rezult += "\n"
//            for (indexJ in ourHarp.allnote.indices) {
//                if (usersTabs[indexI] == ourHarp.allnote[indexJ].second) {
//                    val newIndexJ = checkMargin(indexJ, temp)
//                    rezult += " " + selectedHarp.allnote[newIndexJ].second
//                    break
//                }
//            }
//        }
//        return rezult
//    }


    private fun changeTabs(ourHarp: Harp, selectedHarp: Harp, text: String): String {
        val regex = "<(\\S+)>".toRegex()
        return regex.replace(text) { oldValue -> setNewValue(oldValue.value.dropLast(1).drop(1), ourHarp, selectedHarp) } // тут меняем ноту на нужную
    }

    private fun setNewValue(oldValue: String, ourHarp: Harp, selectedHarp: Harp): String {
        var result = ""
        var value=oldValue
        val temp = checkDifferencePosition(ourHarp.position, selectedHarp.position)
        if (oldValue.contains("\n"))
            result += "\n"

        if (oldValue.contains("+3") && ourHarp.tuning != PADDY) {
            value = "-2"
        }
        for (indexJ in ourHarp.allnote.indices) {
            if (value == ourHarp.allnote[indexJ].second) {
                val newIndexJ = checkMargin(indexJ, temp)
                result += " " + selectedHarp.allnote[newIndexJ].second
                break
            }
        }
        return result
    }


    val majorScale = listOf(2, 2, 1, 2, 2, 2, 1)
    val minorScale = listOf(2, 1, 2, 2, 1, 2, 2)
    val bluesScale = listOf(3, 2, 1, 1, 3, 2)
    val pentamajorScale = listOf(2, 2, 3, 2, 3)
    val pentaminorScale = listOf(3, 2, 2, 3, 2)

    const val RIHTER = 0
    const val PADDY = 1
    const val COUNTRY = 2
    const val MINOR = 3


    val TABS = arrayOf("+1", "-1'", "-1", "1*", "+2", "-2''", "-2'", "-2", "-3'''", "-3''", "-3'", "-3", "+4", "-4'",
            "-4", "4*", "+5", "-5", "5*", "+6", "-6'", "-6", "6*", "-7", "+7", "-7*", "-8", "8'", "+8", "-9", "9'",
            "+9", "-9*", "-10", "10''", "10'", "+10", "-10*", "", "", "", "", "", "", "", "", "", "", "")
    val NOTES = arrayOf("G", "Ab", "A", "Bb", "B", "C", "C#", "D", "Eb", "E", "F", "F#")

    val STROI = arrayOf("Rihter", "Paddy", "Country", "Нат. Минор")

    fun clearView(activity: Activity) {
        var hole: TextView
        for (i in SecondActivity.positionsGrid.indices) {
            hole = activity.findViewById(SecondActivity.positionsGrid[i])
            hole.text = ""
            val defaultColor = activity.resources.getColor(R.color.Default)
            if (hole.currentTextColor != defaultColor) {
                hole.setTextColor(defaultColor)
            }

        }
    }


    fun clearView2(layout: GridLayout) {
        val childCount = layout.childCount
        for (i in 0 until childCount) {
            val textView = layout.getChildAt(i) as TextView
            textView.text = ""
        }
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


    fun checkDifferencePosition(n: Int, z: Int): Int {
        val temps = z - n
        for (i in -1 downTo -11) {
            if (temps == i) return 12 - -i
        }
        return temps
    }


    val scaleNote = object : HashMap<String, Int>() {
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


}

fun View.makeSelectable() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}



