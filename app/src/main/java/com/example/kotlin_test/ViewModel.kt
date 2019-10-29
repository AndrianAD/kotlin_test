package com.example.kotlin_test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ViewModel : AndroidViewModel(Application()) {

    var inPutText: MutableLiveData<String> = MutableLiveData()
    var result: MutableLiveData<String> = MutableLiveData()

    init {
        inPutText.value = ""
        result.value = ""
    }
}