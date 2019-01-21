package com.example.kotlin_test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlin_test.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var message = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setText("Привет первое приложение!")
        Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show()



        button.setOnClickListener {
            message++
            textView.setText(message.toString())
        }


        textView.setOnClickListener {
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }


}
