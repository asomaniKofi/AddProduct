package com.example.fragmentscanner.ui.fragments.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentscanner.R
import com.example.fragmentscanner.util.Council
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AreaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AreaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var areaList: ListView
    private var council:Council? = null

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
        return inflater.inflate(R.layout.fragment_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        areaList = view?.findViewById(R.id.binListView)
        council = Council()
        council!!.restore(activity?.getSharedPreferences("App_Preferences", AppCompatActivity.MODE_PRIVATE))
        var areaArray = arrayOf<String>()
        if(council?.Areas?.size!! > 0){
            for(area in council?.Areas!!){
                areaArray+= area
            }
        }
        val arrayAdapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, areaArray) }
        areaList.adapter = arrayAdapter

        var totalHeight = 0
        for (i in 0 until arrayAdapter?.getCount()!!) {
            val listItem: View = arrayAdapter.getView(i, null, areaList)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        var params = areaList.layoutParams
        params.height = totalHeight + (areaList.dividerHeight * (arrayAdapter.count -1))
        areaList.layoutParams = params
        areaList.requestLayout()
        areaList.setOnItemClickListener { parent, view, position, id ->
            val selectedFrag = SelectedAreaFragment()
            val data = Bundle()
            data.putString("AreaName",council?.Areas!![position])
            selectedFrag.arguments = data
            parentFragmentManager.beginTransaction().replace(this.id,selectedFrag).commit()
            activity?.fragmentManager?.popBackStack();
        }
        areaList.visibility = View.VISIBLE
        activity?.title = arguments?.getString("CouncilName")?.capitalize(Locale.ROOT)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AreaFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AreaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}