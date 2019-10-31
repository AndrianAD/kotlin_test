package com.example.kotlin_test

import com.example.kotlin_test.Util.COUNTRY
import com.example.kotlin_test.Util.MINOR
import com.example.kotlin_test.Util.PADDY
import com.example.kotlin_test.Util.RIHTER

object GridPosition {


    fun getSroi(stroi: Int): IntArray {
        when (stroi) {
            RIHTER -> {
                return getRihter()
            }
            PADDY -> {
                return getPaddy()
            }
            COUNTRY -> {
                return getCountry()
            }
            MINOR -> {
                return getNaturalMinor()
            }
            else -> {
                return getRihter()
            }
        }
    }

    fun getRihter(): IntArray {
        return intArrayOf(R.id.b10, R.id.b40, R.id.b30, R.id.b00, R.id.b11, R.id.b51, R.id.b41, R.id.b31,
                R.id.b62, R.id.b52, R.id.b42, R.id.b32, R.id.b13, R.id.b43, R.id.b33, R.id.b03, R.id.b14,
                R.id.b34, R.id.b04, R.id.b15, R.id.b45, R.id.b35, R.id.b05, R.id.b36, R.id.b16, R.id.b46,
                R.id.b37, R.id.b07, R.id.b17, R.id.b38, R.id.b08, R.id.b18, R.id.b48, R.id.b39, R.id.b009,
                R.id.b09, R.id.b19, R.id.b49)
    }

    fun getPaddy(): IntArray {
        val positionsIdPaddy = getRihter()
        positionsIdPaddy[8] = R.id.b01
        positionsIdPaddy[9] = R.id.b12
        return positionsIdPaddy
    }

    fun getNaturalMinor(): IntArray {
        val positionsIdNaturalMinor = getRihter()
        positionsIdNaturalMinor[3] = R.id.b11
        positionsIdNaturalMinor[4] = R.id.b61
        positionsIdNaturalMinor[8] = R.id.b52
        positionsIdNaturalMinor[9] = R.id.b42
        positionsIdNaturalMinor[10] = R.id.b32
        positionsIdNaturalMinor[11] = R.id.b02
        positionsIdNaturalMinor[15] = R.id.b14
        positionsIdNaturalMinor[16] = R.id.b44
        positionsIdNaturalMinor[22] = R.id.b36
        positionsIdNaturalMinor[23] = R.id.b06
        positionsIdNaturalMinor[27] = R.id.b17
        positionsIdNaturalMinor[28] = R.id.b47
        return positionsIdNaturalMinor
    }

    fun getCountry(): IntArray {
        val positionsIdCountry = getRihter()
        positionsIdCountry[17] = R.id.b44
        positionsIdCountry[18] = R.id.b34
        return positionsIdCountry
    }


}