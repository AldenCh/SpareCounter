package com.example.sparecounter.statrecord

class Table{
    private val table = mutableMapOf<String,Spare>()

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

    public fun getTable(): MutableMap<String, Spare>{
        return table
    }

    override fun toString(): String {
        return table.toString()
    }
}