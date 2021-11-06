package com.example.addproduct
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result

class APILoader {
    companion object {
        val productAPI = "https://addproductappapi.herokuapp.com/api/product/"
        fun load(barcode:String,product:String,material:String) = {
//            val bodyJson = """
//                { "ProductID" : ${barcode},
//                   "ProductName" : ${product},
//                   "Material" :${material}
//                }"""
            val (request,response,result) = Fuel.post(productAPI)
                .header(Headers.CONTENT_TYPE, "application/json")
                .body("{ 'ProductID' : ${barcode},'ProductName' : ${product},'Material' :${material} }")
                .response()
        }
    }

}