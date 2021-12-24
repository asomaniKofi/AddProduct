package com.example.fragmentscanner.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.fragmentscanner.R
import com.example.fragmentscanner.ui.fragments.location.NewLocationFragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var locationText:TextView;
    private lateinit var areaText:TextView;
    private lateinit var areaHeader:TextView;
    private lateinit var locationBtn:Button
    private lateinit var location:String
    private lateinit var area:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationText = view?.findViewById(R.id.savedLocationText)!!
        areaText = view?.findViewById(R.id.savedAreaText)!!
        locationBtn = view?.findViewById(R.id.locationButton)!!
        areaHeader = view?.findViewById(R.id.areaHeader)!!
        locationBtn.setOnClickListener {
            val newFrag = NewLocationFragment()
            parentFragmentManager.beginTransaction().replace(this.id,newFrag).commit()
        }

        if(location.isNullOrEmpty() && area.isNullOrEmpty()){
            locationBtn.visibility = View.VISIBLE
        }else{
            areaText.text = area
            locationText.text = location
            areaHeader.visibility = View.VISIBLE
            locationText.visibility = View.VISIBLE
            areaText.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        location = activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE)?.getString("Location","").toString()
        area = activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE)?.getString("Area","").toString()
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocationFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}