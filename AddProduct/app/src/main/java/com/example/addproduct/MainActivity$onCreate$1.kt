package com.example.addproduct

import android.view.View
import com.google.zxing.integration.android.IntentIntegrator


internal class `MainActivity$onCreate$1`(  /* synthetic */val `this$0`: MainActivity) :
    View.OnClickListener {
    override fun onClick(it: View) {
        val intentIntegrator = IntentIntegrator(`this$0`)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt("SCAN")
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }
}