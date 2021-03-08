package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        val FILE_NAME = "kmlogger_datafile.txt"
        val file = File(activity.filesDir, FILE_NAME)
        val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
        println("### reading file : ###")
        println("readResult=$readResult")
        view.findViewById<TextView>(R.id.scroll_text).text = readResult

//        val bufferedReader: BufferedReader = FileInputStream(file).bufferedReader()
//        val inputString = bufferedReader.use { it.readText()}
//        println("### reading file : ###")
//        println("readResult=$inputString")
//        view.findViewById<TextView>(R.id.scroll_text).text = inputString


        view.findViewById<Button>(R.id.button_third).setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }
}