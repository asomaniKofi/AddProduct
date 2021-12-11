package com.example.fragmentscanner.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
/**
 * Object to parse data we get back from our ProductAPI
 */
class Product {
    @JsonProperty("ProductID")
    val ProductID:Long? = null
    @JsonProperty("ProductName")
    val ProductName:String? = null
    @JsonProperty("Material")
    val Material:String? = null
}