package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.TextView
import androidx.core.util.lruCache
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileInputStream
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

//

val mutableListArrayOnePathType = object : TypeToken<MutableList<OnePath>>() {}.type
 var paths : MutableList<OnePath> = mutableListOf(OnePath("12/12/2012", 0 , "nowhere"))

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

        createfileifdoesnotexist(file)

        view.findViewById<Button>(R.id.button_newentry).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_history).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }
        view.findViewById<Button>(R.id.button_resetfile).setOnClickListener {
            deletedb(file)
        }

        var totalDistance : Int = 0
        paths.forEachIndexed{ idx, path -> totalDistance = totalDistance + path.distance.toInt()}
//        var totalDistanceStr = totalDistance.toString()
        view.findViewById<TextView>(R.id.textview_first).text = "Distance Totale:\n ${totalDistance}Km"

    }

    fun deletedb(file: File) {
        if (!file.exists()) {
            file.createNewFile()
            println("######### create empty kmlogger_datafile.txt")
        } else {
            println("######### File already created - simply delete it and recreate it, empty")
            file.delete()
            file.createNewFile()
        }


        var path1 = OnePath("12/12/2021", 12, "Cagnes")
        var path2 = OnePath("03/03/2021", 4, "Mouans")
        paths.clear()
        paths.add(path1)
        paths.add(path2)
        var gson = Gson()
        var jsonString: String = gson.toJson(paths)
        file.writeText(jsonString)
        println("### init file with 2 entries : ###")
        println(jsonString)
    }

    fun createfileifdoesnotexist(file: File) {
        if (!file.exists()) {
            file.createNewFile()
            deletedb(file)
            println("######### create empty kmlogger_datafile.txt")
        } else {
            println("######### File already exists. Read it and initialize paths object")
            var gson = Gson()
            val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
            println("read fromJson: ")
            paths = gson.fromJson(readResult, mutableListArrayOnePathType)
            paths.forEachIndexed{ idx, path -> println("$idx: ${path.toBasicString()}") }

        }
    }
}