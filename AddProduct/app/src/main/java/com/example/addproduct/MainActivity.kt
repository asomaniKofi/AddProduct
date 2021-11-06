package com.example.addproduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {
    var scanBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("AddProduct")
        val findViewById: View = findViewById<View>(R.id.scanButton)
        val button = findViewById as Button
        scanBtn = button
        button.setOnClickListener(`MainActivity$onCreate$1`(this))
    }

    /* access modifiers changed from: protected */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null) {
            super.onActivityResult(requestCode, resultCode, data)
        } else if (result.getContents() == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("Barcode", result.getContents())
            startActivity(intent)
        }
    }
}