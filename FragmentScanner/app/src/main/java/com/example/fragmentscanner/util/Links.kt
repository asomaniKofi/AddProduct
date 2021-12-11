package com.example.fragmentscanner.util

class Links {

    companion object{
        private val areaAPI = "https://locationareaapi.herokuapp.com/api/locations/areas/?area=mockArea"
        private var productAPI = "https://addproductappapi.herokuapp.com/api/product/?barcode=fakebarcode"
        private val locationAPI = "https://locationareaapi.herokuapp.com/api/locations/?location=mockLocation"
        fun getLink(key:String,value:String):String{
            var result = ""
            when(key){
                "Area" -> {
                    result = areaAPI.replace("mockArea",value)
                    return result
                }
                "Product" -> {
                    result = productAPI.replace("fakebarcode",value)
                    return result
                }
                "Location" -> {
                    result = locationAPI.replace("mockLocation",value)
                    return result
                }
            }
            return ""
        }
    }

}