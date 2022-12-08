package com.example.sparecounter.statrecord

import kotlin.math.roundToInt

class Spare constructor(id: String){
    public final val ID: String = id
    private var makes: Int = 0
    private var misses: Int = 0
    private var percentage: Double = 0.0

    constructor(id: String, make: Int, miss: Int) : this(id){
        makes = make
        misses = miss
        updatePercentage()
    }

    constructor(id: String, make: Int, miss: Int, percent: Double) : this(id){
        makes = make
        misses = miss
        percentage = percent
    }

    public fun editStats(isMake: Boolean, value: Int){
        if (isMake){
            makes += value
            if (makes < 0){
                makes = 0
            }
        }
        else{
            misses += value
            if (misses < 0){
                misses = 0
            }
        }
        updatePercentage()
    }

    public fun getMakes(): Int{
        return makes
    }

    public fun getMisses(): Int{
        return misses
    }

    public fun getPercentage(): Double{
        return percentage
    }

    private fun updatePercentage(){
        if (makes+misses != 0){
            percentage = ((makes.toDouble()/(makes+misses).toDouble())*10000.0).roundToInt().toDouble()/100.0
        }
        else{
            percentage = 0.0
        }
    }

    override fun toString(): String {
        return "\t\t Spare: $ID\n\t\t Makes: $makes\n\t\tMisses: $misses\n\tPercentage: $percentage%"
    }
}