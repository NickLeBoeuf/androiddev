package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.kmlogger.R.id.action_SecondFragment_to_FirstFragment
import com.example.kmlogger.R.id.action_SecondFragment_to_ThirdFragment
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    lateinit var datePicker: DatePickerHelper
    var kmentry : Int = 0
    lateinit var dateEntry : String
    lateinit var itineraryEntry : String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//
        val activity = activity as Context
        datePicker = DatePickerHelper(activity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        val FILE_NAME = "kmlogger_datafile.txt"
        val file = File(activity.filesDir, FILE_NAME)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH) + 1
        var monthStr = if (month < 10) "0${month}" else "${month}"
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dayStr = if (day < 10) "0${day}" else "${day}"
        dateEntry = "$dayStr/$monthStr/$year"

        view.findViewById<TextView>(R.id.textDate).text = dateEntry
        view.findViewById<TextView>(R.id.textDate).setOnClickListener{showDatePickerDialog(view)}

        view.findViewById<Button>(R.id.button_secondback).setOnClickListener {
            findNavController().navigate(action_SecondFragment_to_FirstFragment)
        }

        view.findViewById<Chip>(R.id.chip_plus1).setOnClickListener{ addKm(1,view) }
        view.findViewById<Chip>(R.id.chip_minus1).setOnClickListener{ addKm(-1,view) }
        view.findViewById<Chip>(R.id.chip_plus10).setOnClickListener{ addKm(10,view) }
        view.findViewById<Chip>(R.id.chip_minus10).setOnClickListener{ addKm(-10,view) }
        view.findViewById<Chip>(R.id.chip_plus100).setOnClickListener{ addKm(100,view) }
        view.findViewById<Chip>(R.id.chip_minus100).setOnClickListener{ addKm(-100,view) }

        view.findViewById<Button>(R.id.button_validateentry).setOnClickListener{ writeNewEntry(file,view)}
//          view.findViewById<Button>(R.id.button_validateentry).text = "Changed"


    }

    fun writeNewEntry(file : File,view :View) {
      // create new entry with kmEntry dateEntry and destinationEntry and save it in JSON file
        itineraryEntry = view.findViewById<EditText>(R.id.textInputEditItineraire).text.toString()
        var newpath = OnePath(dateEntry,kmentry,itineraryEntry)
        paths.add(newpath)
        var gson = Gson()
        var jsonString: String = gson.toJson(paths)
        file.writeText(jsonString)
        findNavController().navigate(action_SecondFragment_to_ThirdFragment)
    }

    fun addKm(value: Int,view : View) {
        kmentry = kmentry + value
        view.findViewById<TextView>(R.id.kmNumber).text = kmentry.toString() + " Km"
    }

    private fun showDatePickerDialog(view: View) {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                dateEntry = "${dayStr}/${monthStr}/${year}"
                view.findViewById<TextView>(R.id.textDate).text = dateEntry
            }
        })
    }





}
