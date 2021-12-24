package com.example.fragmentscanner.util
import android.app.Application
import android.content.SharedPreferences
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
/**
 * Class for the Area object we get back from our LocationAPI
 */
class Area: Application() {
    var restored = false
    @JsonProperty("Name")
    var Name: String? = null
    @JsonProperty("BinOne")
    var BinOne: Bin? = null
    @JsonProperty("BinTwo")
    var BinTwo: Bin? = null
    @JsonProperty("BinThree")
    var BinThree: Bin? = null
    @JsonProperty("BinFour")
    var BinFour: Bin? = null
    @JsonProperty("BinFive")
    var BinFive: Bin? = null
    @JsonProperty("BinSix")
    var BinSix: Bin? = null
    @JsonProperty("BinSeven")
    var BinSeven: Bin? = null
    @JsonProperty("BinEight")
    var BinEight: Bin? = null

    fun save(preferences: SharedPreferences?){
        val editor = preferences?.edit()
        if(!Name.isNullOrBlank()){
            editor?.putString("CouncilAreaName",Name)
            editor?.commit()
            editor?.apply()
        }
        if(BinOne !== null){
            BinOne?.save("BinOne",preferences)
        }
        if(BinTwo !== null){
            BinTwo?.save("BinTwo",preferences)
        }
        if(BinThree !== null){
            BinThree?.save("BinThree",preferences)
        }
        if(BinFour !== null){
            BinFour?.save("BinFour",preferences)
        }
        if(BinFive !== null){
            BinFive?.save("BinFive",preferences)
        }
        if(BinSix !== null){
            BinSix?.save("BinSix",preferences)
        }
        if(BinSeven !== null){
            BinSeven?.save("BinSeven",preferences)
        }
        if(BinEight !== null){
            BinEight?.save("BinEight",preferences)
        }
    }

    fun restore(preferences: SharedPreferences?){
        if(preferences !== null || (preferences !== null && !preferences.getString("AreaName","").isNullOrEmpty())){
            val parser = Klaxon()
            Name = preferences.getString("CouncilAreaName","")
            BinOne = preferences.getString("BinOne","")?.let { parser.parse<Bin>(it) }
            BinTwo = preferences.getString("BinTwo","")?.let { parser.parse<Bin>(it) }
            BinThree = preferences.getString("BinThree","")?.let { parser.parse<Bin>(it) }
            BinFour = preferences.getString("BinFour","")?.let { parser.parse<Bin>(it) }
            BinFive = preferences.getString("BinFive","")?.let { parser.parse<Bin>(it) }
            BinSix = preferences.getString("BinSix","")?.let { parser.parse<Bin>(it) }
            BinSeven = preferences.getString("BinSeven","")?.let { parser.parse<Bin>(it) }
            BinEight = preferences.getString("BinEight","")?.let { parser.parse<Bin>(it) }

            if(BinOne == null || BinTwo == null || BinThree == null || BinFour == null || BinFive == null || BinSix == null || BinSeven == null || BinEight == null ){
                restored = false
            }else if(BinOne !== null || BinTwo !== null || BinThree !== null || BinFour !== null || BinFive !== null || BinSix !== null || BinSeven !== null || BinEight !== null){
                restored = true
            }
        }
    }

    fun getResults(value:String):Array<String> {

        if(BinOne !== null){
            if(BinOne?.BinContents?.indexOf(value)!! > -1 || BinOne?.BinContents?.contains(value)!!){
                return arrayOf(BinOne?.BinName!!,value)
            }
        }

        if(BinTwo !== null){
            if(BinTwo?.BinContents?.indexOf(value)!! > -1 || BinTwo?.BinContents?.contains(value)!!){
                return arrayOf(BinTwo?.BinName!!,value)
            }
        }

        if(BinThree !== null){
            if(BinThree?.BinContents?.indexOf(value)!! > -1 || BinThree?.BinContents?.contains(value)!!){
                return arrayOf(BinThree?.BinName!!,value)
            }
        }

        if(BinFour !== null){
            if(BinFour?.BinContents?.indexOf(value)!! > -1 || BinFour?.BinContents?.contains(value)!!){
                return arrayOf(BinFour?.BinName!!,value)
            }
        }

        if(BinFive !== null){
            if(BinFive?.BinContents?.indexOf(value)!! > -1 || BinFive?.BinContents?.contains(value)!!){
                return arrayOf(BinFive?.BinName!!,value)
            }
        }

        if(BinSix !== null){
            if(BinSix?.BinContents?.indexOf(value)!! > -1 || BinSix?.BinContents?.contains(value)!!){
                return arrayOf(BinSix?.BinName!!,value)
            }
        }

        if(BinSeven !== null){
            if(BinSeven?.BinContents?.indexOf(value)!! > -1 || BinSeven?.BinContents?.contains(value)!!){
                return arrayOf(BinSeven?.BinName!!,value)
            }
        }

        if(BinEight !== null){
            if(BinEight?.BinContents?.indexOf(value)!! > -1 || BinEight?.BinContents?.contains(value)!!){
                return arrayOf(BinEight?.BinName!!,value)
            }
        }

        return arrayOf("")
    }

    fun getIDs(): Array<String>{
        if(restored){
            return arrayOf(BinOne?.Id!!,BinTwo?.Id!!,BinThree?.Id!!,BinFour?.Id!!,BinFive?.Id!!,BinSix?.Id!!,BinSeven?.Id!!,BinEight?.Id!!)
        }
        return arrayOf("")
    }

    fun getBin(index:Int): Bin{
        return arrayOf(BinOne!!,BinTwo!!,BinThree!!,BinFour!!,BinFive!!,BinSix!!,BinSeven!!,BinEight!!)[index]
    }
}