package com.example.fragmentscanner.ui.fragments.bins

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.fragmentscanner.R
import com.example.fragmentscanner.ui.fragments.BinFragment
import com.example.fragmentscanner.ui.fragments.LocationFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Page where we display bin information
 */
class SelectedBinFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var name: TextView
    private lateinit var type: TextView
    private lateinit var description: TextView
    private lateinit var occurrences: TextView
    private lateinit var weekInfo: TextView
    private lateinit var nextDate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        name = view.findViewById(R.id.binName)
        type = view.findViewById(R.id.binType)
        description = view.findViewById(R.id.binDescription)
        occurrences = view.findViewById(R.id.binOccurrence)
        weekInfo = view.findViewById(R.id.binWeekData)
        nextDate = view.findViewById(R.id.binDate)
        val fragment = this
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val frag = BinFragment()
                parentFragmentManager.beginTransaction().replace(fragment.id,frag).commit()
                activity?.fragmentManager?.popBackStack();
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)

        val binNameText = arguments?.getString("BinName")
        val binTypeText = arguments?.getString("BinType")
        val binDescriptionText = arguments?.getString("BinDescription")
        val binOccurrenceValue = arguments?.getInt("BinOccurrence",0)
        val binNextDateText = arguments?.getString("BinNextDate")
        val binWeekStartText = arguments?.getString("BinWeekStart")
        val binWeekEndText = arguments?.getString("BinWeekEnd")

        name.text = "$binNameText"
        name.visibility = View.VISIBLE

        type.text = "$binTypeText"
        type.visibility = View.VISIBLE

        description.text = "$binDescriptionText"
        description.visibility = View.VISIBLE

        occurrences.text = "Every $binOccurrenceValue week(s)"
        occurrences.visibility = View.VISIBLE

        nextDate.text = "Next Collection Date: $binNextDateText"
        nextDate.visibility = View.VISIBLE

        if((!binWeekStartText.isNullOrBlank() || !binWeekStartText.isNullOrEmpty()) && (!binWeekEndText.isNullOrBlank() || !binWeekEndText.isNullOrEmpty())){
            weekInfo.text = "$binWeekStartText - $binWeekEndText"
            weekInfo.visibility = View.VISIBLE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectedBinFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectedBinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}