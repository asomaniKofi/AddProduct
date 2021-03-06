package com.example.fragmentscanner.ui.fragments.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.fragmentscanner.R
import com.example.fragmentscanner.ui.fragments.LocationFragment
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Page which will display Product information and what bin it should go in
 */
class ResultFragment(enabled: Boolean) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var Name: TextView
    private lateinit var Packaging: TextView
    private lateinit var Bin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Name = view.findViewById(R.id.productName)
        Packaging = view.findViewById(R.id.productPackaging)
        Bin = view.findViewById(R.id.productBin)
        Name.text =  arguments?.getString("ProductName")
        Packaging.text = arguments?.getString("PackagingType")?.capitalize(Locale.UK)
        Bin.text =  arguments?.getString("BinType")
        val fragment = this
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val frag = LocationFragment()
                parentFragmentManager.beginTransaction().replace(fragment.id,frag).commit()
                activity?.fragmentManager?.popBackStack();
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment(enabled = true).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}