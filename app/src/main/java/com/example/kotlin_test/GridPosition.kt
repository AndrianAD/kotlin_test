package com.example.kotlin_test

import java.util.*

object GridPosition {
    fun getRihter(): IntArray {
        return intArrayOf(R.id.b10, R.id.b40, R.id.b30, R.id.b00, R.id.b11, R.id.b51, R.id.b41, R.id.b31,
                R.id.b62, R.id.b52, R.id.b42, R.id.b32, R.id.b13, R.id.b43, R.id.b33, R.id.b03, R.id.b14,
                R.id.b34, R.id.b04, R.id.b15, R.id.b45, R.id.b35, R.id.b05, R.id.b36, R.id.b16, R.id.b46,
                R.id.b37, R.id.b07, R.id.b17, R.id.b38, R.id.b08, R.id.b18, R.id.b48, R.id.b39, R.id.b009,
                R.id.b09, R.id.b19, R.id.b49)
    }

    fun getPaddy(): IntArray {
        var positions_id_Paddy = getRihter()
        positions_id_Paddy[8] = R.id.b01
        positions_id_Paddy[9] = R.id.b12
        return positions_id_Paddy
    }

    fun getNaturalMinor(): IntArray {
        var positions_id_Natural_Minor = getRihter()
        positions_id_Natural_Minor[3] = R.id.b11
        positions_id_Natural_Minor[4] = R.id.b61
        positions_id_Natural_Minor[8] = R.id.b52
        positions_id_Natural_Minor[9] = R.id.b42
        positions_id_Natural_Minor[10] = R.id.b32
        positions_id_Natural_Minor[11] = R.id.b02
        positions_id_Natural_Minor[15] = R.id.b14
        positions_id_Natural_Minor[16] = R.id.b44
        positions_id_Natural_Minor[22] = R.id.b36
        positions_id_Natural_Minor[23] = R.id.b06
        positions_id_Natural_Minor[27] = R.id.b17
        positions_id_Natural_Minor[28] = R.id.b47
        return positions_id_Natural_Minor
    }

    fun getCountry(): IntArray {
        var positions_id_Country = getRihter()
        positions_id_Country[17] = R.id.b44
        positions_id_Country[18] = R.id.b34
        return  positions_id_Country
    }


}