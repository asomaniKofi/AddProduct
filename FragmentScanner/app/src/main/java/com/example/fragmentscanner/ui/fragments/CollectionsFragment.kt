package com.example.fragmentscanner.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.fragmentscanner.R
import com.example.fragmentscanner.util.Area
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ColletionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ColletionsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var bins: ListView
    private lateinit var calendar: java.util.Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        calendar = java.util.Calendar.getInstance(Locale.ENGLISH)
        bins = view.findViewById(R.id.binDateList)

        val selectedArea = Area()
        selectedArea.restore(activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE))

        val arrayAdapter = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_list_item_1,selectedArea.getIDs())
        bins.adapter = arrayAdapter

        bins.setOnItemClickListener { _, _, position, _ ->
            val selectedBin = selectedArea.getBin(position)
            if(selectedBin.BinCollectionWeekStart.isNullOrEmpty() || selectedBin.BinCollectionWeekEnd.isNullOrEmpty()){
                val date = selectedBin.BinNextDate?.split("-")
                val occurrence = selectedBin.BinOccurence.toString()
                val intent = Intent(Intent.ACTION_INSERT)

                date?.get(0)?.let { calendar.set(it.toInt(),selectedBin.getDate(date[1]),date[2].toInt(),5,0) }

                intent.type = "vnd.android.cursor.item/event"
                intent.data = CalendarContract.Events.CONTENT_URI
                intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
                intent.putExtra("beginTime", calendar.timeInMillis)
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=$occurrence")
                intent.putExtra("endTime", calendar.timeInMillis)
                intent.putExtra("title", "${selectedBin.BinName} Collection Day")
                startActivity(intent);

            }else{
                val startDate = selectedBin.BinCollectionWeekStart.split("-")
                val endDate = selectedBin.BinCollectionWeekEnd.split("-")
                val endCalendar = java.util.Calendar.getInstance(Locale.ENGLISH)
                val occurrence = selectedBin.BinOccurence.toString()
                val intent = Intent(Intent.ACTION_INSERT)

                startDate[0].let { calendar.set(it.toInt(),selectedBin.getDate(startDate[1]),startDate[2].toInt(),5,0) }
                endCalendar.set(endDate[0].toInt(),selectedBin.getDate(endDate[1]),endDate[2].toInt(),5,0)

                intent.type = "vnd.android.cursor.item/event"
                intent.data = CalendarContract.Events.CONTENT_URI
                intent.putExtra(CalendarContract.Events.HAS_ALARM, 1)
                intent.putExtra("beginTime", calendar.timeInMillis)
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;INTERVAL=$occurrence")
                intent.putExtra("endTime", endCalendar.timeInMillis)
                intent.putExtra("title", "${selectedBin.BinName} Collection Week")

                startActivity(intent);
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
         * @return A new instance of fragment ColletionsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ColletionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}