package com.example.kotlin_test.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.kotlin_test.*
import com.example.kotlin_test.Data.Harp
import com.example.kotlin_test.SecondActivity.Companion.harp1
import com.example.kotlin_test.SecondActivity.Companion.harp2
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel


class SettingFragment : androidx.fragment.app.Fragment() {
    lateinit var viewModel: ViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_setting, container, false)


        viewModel = getSharedViewModel()
        view.seekBarKey.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                harp1.value = Harp(position = progress)
                textKey.text = harp1.value!!.keyOfHarp
                viewModel.result.value = Util.getResult(harp1.value!!, harp2.value!!, viewModel.inPutText.value.toString())

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


        view.seekBarKey2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                harp2.value = Harp(position = progress)
                textKey2.text = harp2.value!!.keyOfHarp
                viewModel.result.value = Util.getResult(harp1.value!!, harp2.value!!, viewModel.inPutText.value.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })



        view.seekBarStroi.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                Util.clearView(activity as Activity)
                SecondActivity.positionsGrid = GridPosition.getSroi(progress)
                view.textStroi.text = Util.STROI[progress]
                harp1.value = Harp(position = harp1.value!!.position, stroi = progress)
                textKey2.text = harp2.value!!.keyOfHarp
                viewModel.result.value = Util.getResult(harp1.value!!, harp2.value!!, viewModel.inPutText.value.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        view.seekBarStroi2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                Util.clearView(activity as Activity)
                SecondActivity.positionsGrid = GridPosition.getSroi(progress)
                view.textStroi2.text = Util.STROI[progress]
                harp2.value = Harp(position = harp1.value!!.position, stroi = progress)
                textKey2.text = harp2.value!!.keyOfHarp
                viewModel.result.value = Util.getResult(harp1.value!!, harp2.value!!, viewModel.inPutText.value.toString())
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
        activity!!.setting_id.visibility = View.VISIBLE
    }
}
