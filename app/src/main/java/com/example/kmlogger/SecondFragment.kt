package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    lateinit var datePicker: DatePickerHelper
    var kmentry : Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val activity = activity as Context
        datePicker = DatePickerHelper(activity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)

        view.findViewById<TextView>(R.id.textDate).text = "$day / $month / $year"
        view.findViewById<TextView>(R.id.textDate).setOnClickListener{showDatePickerDialog(view)}

        view.findViewById<Button>(R.id.button_secondback).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        view.findViewById<Chip>(R.id.chip_plus1).setOnClickListener{ addKm(1,view) }
        view.findViewById<Chip>(R.id.chip_minus1).setOnClickListener{ addKm(-1,view) }
        view.findViewById<Chip>(R.id.chip_plus10).setOnClickListener{ addKm(10,view) }
        view.findViewById<Chip>(R.id.chip_minus10).setOnClickListener{ addKm(-10,view) }
        view.findViewById<Chip>(R.id.chip_plus100).setOnClickListener{ addKm(100,view) }
        view.findViewById<Chip>(R.id.chip_minus100).setOnClickListener{ addKm(-100,view) }


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
                view.findViewById<TextView>(R.id.textDate).text = "${dayStr}/${monthStr}/${year}"
            }
        })
    }
}
