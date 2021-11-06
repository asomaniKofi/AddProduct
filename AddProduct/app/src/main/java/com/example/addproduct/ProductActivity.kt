package com.example.addproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.addproduct.APILoader


class ProductActivity : AppCompatActivity() {
    var barcodeText: TextView? = null
    var materialText: EditText? = null
    var productText: EditText? = null
    var submitBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val findViewById = findViewById<View>(R.id.barcodeValue)as TextView
        barcodeText = findViewById
        val findViewById2 = findViewById<View>(R.id.productNameValue)
        productText = findViewById2 as EditText
        val findViewById3 = findViewById<View>(R.id.materialValue)
        materialText = findViewById3 as EditText
        val findViewById4 = findViewById<View>(R.id.addButton)
        submitBtn = findViewById4 as Button
        val textView = barcodeText
        textView!!.text = intent.getStringExtra("Barcode")
        submitBtn!!.setOnClickListener {
            val product = productText!!.text.toString();
            val material = materialText!!.text.toString();
            val barcode = textView!!.text.toString();
            submitBtn!!.isEnabled = false;
            if(product.length > 1 || material.length > 1){
                productText!!.isEnabled = false;
                materialText!!.isEnabled = false;
                APILoader.load(barcode,product,material);
            }else{
                Toast.makeText(this, "Please enter all boxes before submitting", Toast.LENGTH_LONG).show()
                submitBtn!!.isEnabled = true;
            }


        }

    }
}