package com.example.kmlogger

data class OnePath (
    var date: String = "date",
    var distance: String = "0",
    var itinerary: String = "Neverland")
{

    constructor(date: String, distance: Int, itinerary: String) : this(date,distance.toString(),itinerary)

    override fun toString(): String {
        return "date: ${this.date} distance: ${this.distance} itinerary: ${this.itinerary}"
    }

    fun toBasicString(): String {
        val kmStr = if (distance.toInt() < 10) "   " else if (distance.toInt() < 100) "  " else " "
        return "${this.date} ${kmStr}${this.distance}Km   ${this.itinerary}"
    }
}