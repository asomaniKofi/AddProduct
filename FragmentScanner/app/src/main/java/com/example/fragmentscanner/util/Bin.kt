package com.example.fragmentscanner.util

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.Gson
import java.io.Serializable
import java.util.*
class Bin: Serializable {
    var Id:String? = null
    @JsonProperty("BinName")
    val BinName:String? = null
    @JsonProperty("BinType")
    val BinType:String? = null
    @JsonProperty("BinDescription")
    val BinDescription:String? = null
    @JsonProperty("BinOccurence")
    val BinOccurence:Int? = null
    @JsonProperty("BinNextDate")
    val BinNextDate:String? = null
    @JsonProperty("BinCollectionWeekStart")
    val BinCollectionWeekStart:String? = null
    @JsonProperty("BinCollectionWeekEnd")
    val BinCollectionWeekEnd:String? = null
    @JsonProperty("BinContents")
    val BinContents: ArrayList<String>? = null

    @SuppressLint("CommitPrefEdits")
    fun save(id:String?, appPreferences: SharedPreferences?){
        if(appPreferences !== null){
            Id = id
            val editor = appPreferences?.edit()
            val gson = Gson()
            val data = gson.toJson(this)
            editor?.putString(Id,data)
            editor?.commit()
            editor?.apply()
        }
    }

    fun getDate(value:String):Int{
        when(value){
            "01" -> return Calendar.JANUARY
            "02" -> return Calendar.FEBRUARY
            "03" -> return Calendar.MARCH
            "04" -> return Calendar.APRIL
            "05" -> return Calendar.MAY
            "06" -> return Calendar.JUNE
            "07" -> return Calendar.JULY
            "08" -> return Calendar.AUGUST
            "09" -> return Calendar.SEPTEMBER
            "10" -> return Calendar.OCTOBER
            "11" -> return Calendar.NOVEMBER
            "12" -> return Calendar.DECEMBER
        }
        return 13
    }
}