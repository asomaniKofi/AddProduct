package com.example.fragmentscanner.ui.fragments.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.fragmentscanner.R
import com.example.fragmentscanner.ui.fragments.scan.ResultFragment
import com.example.fragmentscanner.util.Area
import com.example.fragmentscanner.util.Links
import com.example.fragmentscanner.util.Product
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.zxing.integration.android.IntentIntegrator
import javax.xml.transform.Result

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var scan: Button
    private lateinit var textView: TextView
    private val selectedArea = Area()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        scan = view.findViewById(R.id.barcodeBtn)
        textView = view.findViewById(R.id.loadingScan)
        scan.setOnClickListener {
            val integrator = IntentIntegrator.forSupportFragment(this@CameraFragment)
            integrator.setBeepEnabled(false)
            integrator.setCameraId(0)
            integrator.setPrompt("SCAN BARCODE")
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()
        }
        selectedArea.restore(activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE))
    }

    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents !== null) {
            requestProduct(result.contents)
            textView.visibility = View.VISIBLE
            scan.isEnabled = false
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun requestProduct(barcode: String){
        if(barcode.isNotBlank()){
            Fuel.get(Links.getLink("Product",barcode)).responseJson { _, _, result ->
                try{
                    val parser = Klaxon()
                    val product = parser.parse<Product>(result.get().obj().toString())
                    if(product?.Material?.isNotEmpty() == true){
                        val binResult = selectedArea.getResults(product.Material)
                        createFragment(binResult,product.ProductName!!)
                    }else{
                        Toast.makeText(context, "Packaging Information not available, Please try another product", Toast.LENGTH_LONG).show()
                        textView.visibility = View.INVISIBLE
                        scan.isEnabled = true
                    }
                }catch(e:Exception){
                    Toast.makeText(context, "Packaging Information not available, Please try another product", Toast.LENGTH_LONG).show()
                    textView.visibility = View.INVISIBLE
                    scan.isEnabled = true
                }
            }
        }
    }

    private fun createFragment(results: Array<String>,productName:String){
        val result = ResultFragment(enabled = true)
        val data = Bundle()
        data.putString("BinType",results[0])
        data.putString("PackagingType",results[1])
        data.putString("ProductName",productName)
        result.arguments = data
        parentFragmentManager.beginTransaction().replace(this.id,result).commit()
        activity?.fragmentManager?.popBackStack();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CameraFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}