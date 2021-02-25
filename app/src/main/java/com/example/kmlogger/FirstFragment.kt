package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileInputStream

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        val FILE_NAME = "kmlogger_datafile.txt"
        val file = File(activity.filesDir, FILE_NAME)


        view.findViewById<Button>(R.id.button_newentry).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_history).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
        view.findViewById<Button>(R.id.button_resetfile).setOnClickListener {
            deletedb(file)
        }


        val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
        println("### reading file : ###")
        println("readResult=$readResult")
    }

    fun deletedb(file: File) {
        if (!file.exists()) {
            file.createNewFile()
            println("######### create kmlogger_datafile.txt")
        } else {
            println("######### File already created")
            file.delete()
            file.createNewFile()
        }
        println("### init file with 3 entries ###")
        file.appendText("10/2/2021 25Km Cagnes\n")
        file.appendText("15/2/2021 25Km Cagnes\n")
        file.appendText("18/2/2021 15Km Grasse\n")
    }
}