package com.example.sparecounter.statrecord

class Table{
    // Map of Spares
    private val table = mutableMapOf<String,Spare>()

    // Add a Spare to the map and perform necessary checks
    public fun addEntry(spare: Spare){
        if (!table.contains(spare.ID)) {
            var newSpare: Spare = spare
            if (newSpare.getMakes() < 0){
                newSpare.editStats(true, 0)
            }
            if (newSpare.getMisses() < 0){
                newSpare.editStats(false, 0)
            }
            table[newSpare.ID] = newSpare
        }
        else{
            table[spare.ID]?.editStats(true, spare.getMakes())
            table[spare.ID]?.editStats(false, spare.getMisses())
        }
    }

    // Edit a selected Spare in the table
    public fun editEntry(id: String, isMake: Boolean, value: Int): Spare? {
        if (table.containsKey(id)) {
            table[id]?.editStats(isMake, value)
        }
        else{
            addEntry(Spare(id))
            table[id]?.editStats(isMake, value)
        }
        return table[id]
    }

    // Return the map of Spares
    public fun getTable(): MutableMap<String, Spare>{
        return table
    }

    // toString method
    override fun toString(): String {
        return table.toString()
    }
}