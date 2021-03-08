package com.example.kmlogger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.util.lruCache
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileInputStream
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class OnePath {
    var date: String? = "date"
    var distance: Int = 0
    var itinerary: String = "Neverland"
    constructor() : super() {}
    constructor(date:String, distance: Int, itinerary: String) : super () {
        this.date = date
        this.distance = distance
        this.itinerary = itinerary
    }
}

var paths = mutableListOf<OnePath>()

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



        var path1 = OnePath("12/12/2021", 12, "Cagnes")
        var path2 = OnePath("03/03/2021", 2, "Mouans")
            paths.add(path1)
            paths.add(path2)
//        var paths: HashMap<String, String> =
//            hashMapOf("date" to "03 /03 / 2021", "distance" to "12", "itinerary" to "Cagnes")
        var gson = Gson()
        var jsonString: String = gson.toJson(paths)
        file.writeText(jsonString)

        println("### init file with 2 entries ###")
    }

    fun createfileifdoesnotexist(file: File) {
        if (!file.exists()) {
            file.createNewFile()
            println("######### create kmlogger_datafile.txt")
        } else {
            println("######### File already exists. Read it and initialize paths object")
            var gson = Gson()
//            val readResult = FileInputStream(file).bufferedReader().use { it.readText() }
//            paths = gson.fromJson(readResult, MutableList<OnePath>())
        }
    }
}