package com.example.kotlin_test

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.kotlin_test.Data.Harp
import com.example.kotlin_test.Util.check_difference_position
import kotlinx.android.synthetic.main.activity_main.*


//Функция-расширения (Extension function)
fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}


class MainActivity : AppCompatActivity() {
    private var message: String = ""
    private val TAG = javaClass.simpleName
    private var ourHarp = Harp()
    private var selectedHarp = Harp(position =0)

    private val viewModel: Harp by lazy {
        ViewModelProviders.of(this).get(Harp::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Main Activity ON_CREATE")
        var spinerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Util.STROI)

        // подписка на Lifecycle
        lifecycle.addObserver(Harp())











        button.setOnClickListener {
          //  textView.setText(calculate(ourHarp,selectedHarp))
            startActivity(Intent(this,SecondActivity::class.java))

        }



        left_spinner.adapter = spinerAdapter
        left_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                ourHarp.stroi = Util.STROI[position]
            }
        }


        right_spinner.adapter = spinerAdapter
        right_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedHarp.stroi = Util.STROI[position]
            }
        }


    }







}




//list.withIndex()

//fun invertPositives(list: MutableList<Int>) {
//    for ((index, element) in list.withIndex()) {
//        if (element > 0) {
//            list[index] = -element
//        }
//    }
//}


//    fun negativeList(list: List<Int>): List<Int> {
//        val result = mutableListOf<Int>()
//        for (element in list) {
//            if (element < 0) {
//                result.add(element)
//            }
//        }
//        return result
//    }


//
//
//fun negativeList(list: Set<String>) = list.minus("Irka").plus("Irchik").sorted().drop(2)


//Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show()
//
//var list= setOf("Gloria","Andrian","Irka", "Tima", "Gloria")
//var z = negativeList(list)
//textView.setText(z.joinToString(separator = "!"))


//private fun makeHarp(): LiveData<String> {
//    var harp2 = Harp("Paddy", 5)
//
//    val random: String = Random().nextInt( 9).toString()
//    val random2: String = Random().nextInt( 9).toString()
//    val random3: String = Random().nextInt( 9).toString()
//    val random4: String = Random().nextInt( 9).toString()
//
//
//    //инициальзация MutableLiveData
//    val currentName: MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//
//    currentName.value=Util.get_input_tabs("$random ,$random2 ,$random3 ,$random4 ", harp2).toString()
//    return currentName
//}


//LiveData

//сделит за методом random и выводит в textView
//        Util.random().observe(this, Observer {textView.setText(it) })


//сделит за полем random и выводит в textView
//Util.currentName.observe(this, Observer { textView.setText(it) })
//---------------------------------------------------------------------------
//ViewModelProviders
//  var harpView=ViewModelProviders.of(this).get(Harp::class.java) // тут при повороте будет значения сохраняться
// var harpView = Harp() // так значения не будет сохраняться
//    message= viewModel.randomString()
//      textView.setText(message)

//        var harpView=Harp()
//        message=harpView.randomString()
//        textView.setText(message)


//        textView.setText(Util.randomString())


