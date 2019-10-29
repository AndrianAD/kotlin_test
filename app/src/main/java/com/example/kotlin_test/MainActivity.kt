package com.example.kotlin_test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.Data.Harp





class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Main Activity ON_CREATE")

        // подписка на Lifecycle
        lifecycle.addObserver(Harp())

        startActivity(Intent(this, SecondActivity::class.java))


    }


}


