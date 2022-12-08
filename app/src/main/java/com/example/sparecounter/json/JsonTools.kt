package com.example.sparecounter.json

import com.example.sparecounter.statrecord.Table
import com.example.sparecounter.statrecord.Spare
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.lang.ClassCastException

class JsonTools {
    fun saveJson(inputTable: Table, directory: File): Table {
        var table: Table = inputTable
        val output: Writer
        val storageDirectory = directory
        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs()
        }

        val file = File(storageDirectory, "Records.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        output= BufferedWriter(FileWriter(file))
        output.write("[")
        for (record in table.getTable().values){
            val entry = JSONObject()
            entry.put("ID", record.ID)
                .put("Makes", record.getMakes())
                .put("Misses", record.getMisses())
                .put("Percentage", record.getPercentage())
            if (record != table.getTable().values.last()){
                output.write("\n" + entry.toString() + ",")
            }
            else{
                output.write("\n" + entry.toString())
            }
        }
        output.write("\n]")
        output.close()
        return table
    }

    fun loadTable(directory: File): Table{
        var result: Table = Table()
        val storageDirectory = directory
        val file = File(storageDirectory, "Records.json")
        if (storageDirectory.exists() && file.exists()) {
            file.forEachLine {
                if (it != "[" && it != "]"){
                    val entry = JSONObject(it)
                    val data: Spare = Spare(entry.get("ID").toString(),
                        entry.get("Makes") as Int,
                        entry.get("Misses") as Int,
                        try{
                            (entry.get("Percentage") as Int).toDouble()
                        } catch(e: ClassCastException){
                            entry.get("Percentage") as Double
                        }
                    )
                    result.addEntry(data)
                }
            }
        }
        return result
    }

    fun createSampleJsonData(directory: File){
        val output:Writer
        val storageDirectory = directory
        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs()
        }

        val file = File(storageDirectory, "Records.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        output=BufferedWriter(FileWriter(file))
        output.write("[")

        for (i in 1 .. 4){
            var entry = Spare(i.toString(), 1, 2)
            var json = JSONObject()
            json.put("ID", entry.ID)
                .put("Makes", entry.getMakes())
                .put("Misses", entry.getMisses())
                .put("Percentage", entry.getPercentage() as Double)
            if (i != 4){
                output.write("\n" + json.toString() + ",")
            }
            else{
                output.write("\n" + json.toString())
            }
        }
        output.write("\n]")
        output.close()
    }

    fun clearJsonData(directory: File){
        val output:Writer
        val storageDirectory = directory
        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs()
        }

        val file = File(storageDirectory, "Records.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        output=BufferedWriter(FileWriter(file))
        output.write("")
        output.close()
    }
}