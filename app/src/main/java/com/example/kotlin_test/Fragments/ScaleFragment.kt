package com.example.kotlin_test.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_test.R
import com.example.kotlin_test.SecondActivity
import com.example.kotlin_test.Util
import kotlinx.android.synthetic.main.fragment_scale.*
import kotlinx.android.synthetic.main.fragment_scale.view.*

class ScaleFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.fragment_scale, container, false)




        view.checkBox__major.setOnClickListener {
            var list = SecondActivity.harp1.allnote.slice(Util.majorScale)
            var list2 = SecondActivity.harp1.allnote.slice(Util.majorScale)
        }

        return view
    }


}
