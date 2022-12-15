package com.example.sparecounter.statrecord

import kotlin.math.roundToInt

class Spare constructor(id: String){
    // The pins related to the current spare
    public final val ID: String = id

    // The stats of your spare
    private var makes: Int = 0
    private var misses: Int = 0
    private var percentage: Double = 0.0

    // Constructor for no percentage
    constructor(id: String, make: Int, miss: Int) : this(id){
        makes = make
        misses = miss
        updatePercentage()
    }

    // Constructor including percentage
    constructor(id: String, make: Int, miss: Int, percent: Double) : this(id){
        makes = make
        misses = miss
        percentage = percent
    }

    // Function to update values for a given spare
    // and handle pre-modification checks
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

    // Getter For Spare Makes
    public fun getMakes(): Int{
        return makes
    }

    // Getter For Spare Misses
    public fun getMisses(): Int{
        return misses
    }

    // Getter For Spare Percentage
    public fun getPercentage(): Double{
        return percentage
    }

    // Function to update Spare Percentage
    private fun updatePercentage(){
        if (makes+misses != 0){
            percentage = ((makes.toDouble()/(makes+misses).toDouble())*10000.0).roundToInt().toDouble()/100.0
        }
        else{
            percentage = 0.0
        }
    }

    // toString method
    override fun toString(): String {
        return "\t\t Spare: $ID\n\t\t Makes: $makes\n\t\tMisses: $misses\n\tPercentage: $percentage%"
    }
}