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
import com.example.fragmentscanner.util.Area
import com.example.fragmentscanner.util.Links
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectedAreaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectedAreaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var confirmBtn: Button
    private lateinit var areaText: TextView
    private lateinit var loadingTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        areaText = view?.findViewById(R.id.areaText)
        areaText.text = arguments?.getString("AreaName")
        confirmBtn = view?.findViewById(R.id.confirmbtn)
        loadingTxt = view?.findViewById(R.id.loadingAreaText)

        confirmBtn.setOnClickListener {
            Fuel.get(Links.getLink("Area",areaText.text.toString())).responseJson { request, response, result ->
                try{
                    confirmBtn.isEnabled = false
                    loadingTxt.visibility = View.VISIBLE
                    val areaParser = Klaxon()
                    val area = areaParser.parse<Area>(result.get().obj().toString())
                    area?.save(activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE))
                    val editor = activity?.getSharedPreferences("App_Preferences",Context.MODE_PRIVATE)?.edit()?.putString("Area",arguments?.getString("AreaName"))
                    editor?.commit()
                    editor?.apply()
                    Toast.makeText(context, "Area Saved", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this,MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
//                    startActivity(intent)
                }catch(e:Exception){
                    Toast.makeText(context, "Somethings Gone wrong", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectedAreaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectedAreaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}