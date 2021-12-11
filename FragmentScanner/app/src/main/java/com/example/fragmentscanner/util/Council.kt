package com.example.fragmentscanner.util
import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
/**
 * Class to parse Council data from our LocationAPI
 */
class Council:Serializable {
    val gson = Gson()
    @JsonProperty("Name")
    var Name: String? = null
    @JsonProperty("Areas")
    var Areas: ArrayList<String>? = null
    var saved = false
    @SuppressLint("CommitPrefEdits")
    fun save(preferences: SharedPreferences?){
        val editor = preferences?.edit()
        if(!Name.isNullOrBlank()){
            editor?.putString("CouncilName", Name)
            editor?.commit()
            editor?.apply()
        }
        if(Areas?.size!! > 0){
            val data = gson.toJson(Areas)
            editor?.putString("CouncilAreas", data)
            editor?.commit()
            editor?.apply()
        }
        saved = true
    }
    @SuppressLint("CommitPrefEdits")
    fun restore(preferences: SharedPreferences?){
        Name = preferences?.getString("",null)
        Areas = gson.fromJson(preferences?.getString("CouncilAreas",null), object : TypeToken<ArrayList<String?>?>() {}.type)
    }
}