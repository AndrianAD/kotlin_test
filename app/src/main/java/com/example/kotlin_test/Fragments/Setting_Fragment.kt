package com.example.kotlin_test.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.kotlin_test.Data.Harp

import com.example.kotlin_test.R
import com.example.kotlin_test.SecondActivity
import com.example.kotlin_test.SecondActivity.Companion.harp1
import com.example.kotlin_test.SecondActivity.Companion.harp2
import com.example.kotlin_test.Util
import com.example.kotlin_test.toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Setting_Fragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_setting, container, false)




       view.seekBarKey.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                harp1.value=Harp(position = progress)
                textKey.text= harp1.value!!.key_of_harp
                SecondActivity.liveData.value = Util.getResult(harp1.value!!, harp2.value!!, SecondActivity.inPutText)

            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


        view.seekBarKey2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                harp2.value=Harp(position = progress)
                textKey2.text= harp2.value!!.key_of_harp
                SecondActivity.liveData.value = Util.getResult(harp1.value!!, harp2.value!!, SecondActivity.inPutText)

            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })






        return view
    }

    override fun onStop() {
        super.onStop()
        activity!!.setting_id.visibility=View.VISIBLE
    }
}
