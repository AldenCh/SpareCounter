package com.example.sparecounter

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sparecounter.databinding.ActivityMainBinding
import com.example.sparecounter.json.JsonTools
import com.example.sparecounter.statrecord.*
import com.google.android.material.textfield.TextInputEditText
import java.io.*

class MainActivity : AppCompatActivity() {
    // Table holding all the Spare data
    private var records: Table = Table()

    // Variables to store references to GUI elements
    private var pinList: List<ToggleButton>? = null
    private var makeQuantityField: TextInputEditText? = null
    private var missQuantityField: TextInputEditText? = null
    private var percentagePrompt: TextView? = null
    private var statRowPins: List<TableLayout>? = null
    private var statRowTexts: List<TextView>? = null

    // Variables for the navigation bar
    private lateinit var binding: ActivityMainBinding
    private var spareScreen = NewSpare()
    private var statScreen = Statistics()
    private var settingScreen = Settings()

    // ID of the current Spare shown on the Add Spare screen
    private var id: String = ""

    // Counter to confirm data deletion
    private var clearCounter: Int = 0

    // Json tools variable to handle File IO
    private var jsonTools: JsonTools = JsonTools()

    // Initialize the navigation bar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        replaceFragment(spareScreen)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.new_spare -> replaceFragment(spareScreen)
                R.id.stats -> replaceFragment(statScreen)
                R.id.settings -> replaceFragment(settingScreen)
            }
            true
        }
        records = jsonTools.loadTable(
            File(this.getExternalFilesDir(null), "Storage")
        )
    }

    // Handle switching between screens of the navigation bar
    private fun replaceFragment(fragment: Fragment){
        clearCounter = 0
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    // Initialize the references to the toggle buttons representing pins on
    // the Add Spare Page
    private fun instantiateSpareEntryFields(){
        val onePin: ToggleButton = findViewById(R.id.onePin)
        val twoPin: ToggleButton = findViewById(R.id.twoPin)
        val threePin: ToggleButton = findViewById(R.id.threePin)
        val fourPin: ToggleButton = findViewById(R.id.fourPin)
        val fivePin: ToggleButton = findViewById(R.id.fivePin)
        val sixPin: ToggleButton = findViewById(R.id.sixPin)
        val sevenPin: ToggleButton = findViewById(R.id.sevenPin)
        val eightPin: ToggleButton = findViewById(R.id.eightPin)
        val ninePin: ToggleButton = findViewById(R.id.ninePin)
        val tenPin: ToggleButton = findViewById(R.id.tenPin)
        pinList= listOf(onePin, twoPin, threePin, fourPin, fivePin, sixPin, sevenPin, eightPin, ninePin, tenPin)

        makeQuantityField = findViewById(R.id.makeQuantityField)
        missQuantityField = findViewById(R.id.missQuantityField)
        percentagePrompt = findViewById(R.id.sparePercentagePrompt)
    }

    // Initialize the references to each row of pins on the Add Spare page
    private fun instantiateStatRows(){
        val rowOne: TableLayout = findViewById(R.id.row_1_pins)
        val rowTwo: TableLayout = findViewById(R.id.row_2_pins)
        val rowThree: TableLayout = findViewById(R.id.row_3_pins)
        val rowFour: TableLayout = findViewById(R.id.row_4_pins)
        val rowFive: TableLayout = findViewById(R.id.row_5_pins)
        val rowSix: TableLayout = findViewById(R.id.row_6_pins)
        val rowSeven: TableLayout = findViewById(R.id.row_7_pins)
        val rowEight: TableLayout = findViewById(R.id.row_8_pins)
        val rowNine: TableLayout = findViewById(R.id.row_9_pins)
        val rowTen: TableLayout = findViewById(R.id.row_10_pins)
        statRowPins = listOf(rowOne, rowTwo, rowThree, rowFour, rowFive,
            rowSix, rowSeven, rowEight, rowNine, rowTen)

        val rowOneText: TextView = findViewById(R.id.row_1_stats)
        val rowTwoText: TextView = findViewById(R.id.row_2_stats)
        val rowThreeText: TextView = findViewById(R.id.row_3_stats)
        val rowFourText: TextView = findViewById(R.id.row_4_stats)
        val rowFiveText: TextView = findViewById(R.id.row_5_stats)
        val rowSixText: TextView = findViewById(R.id.row_6_stats)
        val rowSevenText: TextView = findViewById(R.id.row_7_stats)
        val rowEightText: TextView = findViewById(R.id.row_8_stats)
        val rowNineText: TextView = findViewById(R.id.row_9_stats)
        val rowTenText: TextView = findViewById(R.id.row_10_stats)
        statRowTexts = listOf(rowOneText, rowTwoText, rowThreeText, rowFourText,
            rowFiveText, rowSixText, rowSevenText, rowEightText, rowNineText,rowTenText)
    }

    // Update the spares shown on the bottom ten screen
    private fun updateBottomTen(){
        instantiateStatRows()
        val sortedRecords: List<Spare> = records.getTable().values.toList().sortedBy { it.getPercentage() }
        var rowNumber = 0
        for (record in 0 .. sortedRecords.size-1){
            val entry = sortedRecords.get(record)
            if (entry.getMisses() != 0 || entry.getMakes() != 0){
                if (rowNumber == 10){
                    break
                }
                if (entry.getMakes()+entry.getMisses() < 3){
                    continue
                }
                updatePins(statRowPins?.get(rowNumber) as TableLayout, entry.ID)
                updateText(entry, statRowTexts?.get(rowNumber) as TextView)
                rowNumber++
            }
        }
    }


    public fun updateBottomTen(view: View){
        clearCounter = 0
        instantiateStatRows()
        val sortedRecords: List<Spare> = records.getTable().values.toList().sortedBy { it.getPercentage() }
        var rowNumber = 0
        for (record in 0 .. sortedRecords.size-1){
            val entry = sortedRecords.get(record)
            if (entry.getMisses() != 0 || entry.getMakes() != 0){
                if (rowNumber == 10){
                    break
                }
                if (entry.getMakes()+entry.getMisses() < 3){
                    continue
                }
                updatePins(statRowPins?.get(rowNumber) as TableLayout, entry.ID)
                updateText(entry, statRowTexts?.get(rowNumber) as TextView)
                rowNumber++
            }
        }
        Toast.makeText(this, "Reloaded", Toast.LENGTH_SHORT).show()
    }

    // Update the Toggle Buttons representing pins on the home page
    private fun updatePins(pins: TableLayout, id: String){
        ((pins.getChildAt(3) as TableRow).getChildAt(0) as ToggleButton).isChecked = id.contains("1")
        ((pins.getChildAt(2) as TableRow).getChildAt(0) as ToggleButton).isChecked = id.contains("2")
        ((pins.getChildAt(2) as TableRow).getChildAt(1) as ToggleButton).isChecked = id.contains("3")
        ((pins.getChildAt(1) as TableRow).getChildAt(0) as ToggleButton).isChecked = id.contains("4")
        ((pins.getChildAt(1) as TableRow).getChildAt(1) as ToggleButton).isChecked = id.contains("5")
        ((pins.getChildAt(1) as TableRow).getChildAt(2) as ToggleButton).isChecked = id.contains("6")
        ((pins.getChildAt(0) as TableRow).getChildAt(0) as ToggleButton).isChecked = id.contains("7")
        ((pins.getChildAt(0) as TableRow).getChildAt(1) as ToggleButton).isChecked = id.contains("8")
        ((pins.getChildAt(0) as TableRow).getChildAt(2) as ToggleButton).isChecked = id.contains("9")
        ((pins.getChildAt(0) as TableRow).getChildAt(3) as ToggleButton).isChecked = id.contains("0")
    }

    // Update the stats text on the Add Spare screen
    private fun updateText(node: Spare, view: TextView){
        view.text = "Makes:\t\t${node.getMakes()}\nMisses:\t${node.getMisses()}\nPercentage: ${node.getPercentage()}%"
    }

    // Update the text in the Percentage Label, Make and Miss Text Fields
    // based on the selected Spare
    public fun updateSpareFields(view: View){
        clearCounter = 0
        instantiateSpareEntryFields()
        id = ""
        for (pin in 0 .. 9){
            if (pinList?.get(pin)?.isChecked == true){
                if (pin == 9){
                    id += "0"
                }
                else{
                    id += (pin+1).toString()
                }
            }
        }
        if (id != ""){
            if (!records.getTable().contains(id)){
                makeQuantityField?.setText("0")
                missQuantityField?.setText("0")
                percentagePrompt?.text = "Percentage: 0.0%"
            }
            else{
                records.addEntry(Spare(id))
                makeQuantityField?.setText(records.getTable().get(id)?.getMakes().toString())
                missQuantityField?.setText(records.getTable().get(id)?.getMisses().toString())
                percentagePrompt?.text =
                    "Percentage: " + records.getTable().get(id)?.getPercentage().toString()+"%"
            }
        }
        else{
            makeQuantityField?.setText("")
            missQuantityField?.setText("")
            percentagePrompt?.text = "Percentage: -"
        }
    }

    // Functions linked to the increment and decrement buttons under the
    // Miss and Make Text Fields that call for updates to the Table
    public fun updateMake(view: View){
        if (makeQuantityField?.text != null && makeQuantityField?.text?.contains(".") != true){
            updateSpare(true, Integer.parseInt(makeQuantityField?.text.toString()))
        }
    }
    public fun increaseMake(view: View){
        if (makeQuantityField?.text?.contains(".") != true){
            updateSpare(true, 1)
        }
    }
    public fun decreaseMake(view: View){
        if (makeQuantityField?.text?.contains(".") != true){
            updateSpare(true, -1)
        }
    }
    public fun updateMiss(view: View){
        if (missQuantityField?.text != null && missQuantityField?.text?.contains(".") != true){
            updateSpare(false, Integer.parseInt(missQuantityField?.text.toString()))
        }
    }
    public fun increaseMiss(view: View){
        if (missQuantityField?.text?.contains(".") != true){
            updateSpare(false, 1)
        }
    }
    public fun decreaseMiss(view: View){
        if (missQuantityField?.text?.contains(".") != true){
            updateSpare(false, -1)
        }
    }

    // Function to update the Spares in the Table and internal storage
    public fun updateSpare(isMake: Boolean, value: Int){
        clearCounter = 0
        if (id != "" && id != null) {
            if (isMake){
                records.addEntry(Spare(id, value, 0))
                makeQuantityField?.setText(records.getTable().get(id)?.getMakes().toString())
            }
            else{
                records.addEntry(Spare(id, 0, value))
                missQuantityField?.setText(records.getTable().get(id)?.getMisses().toString())
            }
            percentagePrompt?.setText("Percentage: " + records.getTable().get(id)?.getPercentage().toString()+"%")
            saveJson()
        }
    }

    // Function linked to the clear button in the settings page that clears the
    // table and calls the function to clear the record in internal storage too
    public fun buttonClearJsonData(view: View){
        clearCounter++
        if (clearCounter == 3){
            val output:Writer
            val storageDirectory = File(this.getExternalFilesDir(null), "Storage")
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

            spareScreen = NewSpare()
            statScreen = Statistics()
            records = Table()
            id = ""
            val clear: Button = findViewById(R.id.clearButton)
            clear.text ="Clear Data"
            clear.setBackgroundColor(resources.getColor(R.color.blue_gray))
            Toast.makeText(this, "Cleared", Toast.LENGTH_SHORT).show()
            clearCounter = 0
        }else{
            val clear: Button = findViewById(R.id.clearButton)
            clear.text ="Clear Data In ${3-clearCounter} Press(es)"
            if (clearCounter == 1){
                clear.setBackgroundColor(resources.getColor(R.color.yellow))
            }
            else if (clearCounter == 2){
                clear.setBackgroundColor(resources.getColor(R.color.red))
            }
        }
    }

    // Calls the function to save values in the Table to internal storage
    private fun saveJson(){
        jsonTools.saveJson(records, File(this.getExternalFilesDir(null), "Storage"))
    }
}