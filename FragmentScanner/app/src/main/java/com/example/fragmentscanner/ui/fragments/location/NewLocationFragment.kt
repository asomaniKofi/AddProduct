package com.example.fragmentscanner.ui.fragments.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.beust.klaxon.Klaxon
import com.example.fragmentscanner.R
import com.example.fragmentscanner.util.Council
import com.example.fragmentscanner.util.Links
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.google.android.gms.location.*
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Page where we use geocoder to get a council via user location
 */
class NewLocationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var client: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 2
    private var currentLocation: Location? = null
    private var locationSet: Boolean = false
    private var locationCallback: LocationCallback? = null
    private var selectedCouncil: Council? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        client = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        locationCallback = object : LocationCallback(){
            override fun onLocationAvailability(p0: LocationAvailability?) {
                p0
            }
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onLocationResult(p0: LocationResult?) {
                currentLocation = p0?.locations?.get(0)
                getAddress(currentLocation)
                locationSet = true
            }
        }
        selectedCouncil = Council()
        startUpdate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_location, container, false)
    }

    private fun getAddress(location: Location?){
        val coder = Geocoder(context, Locale.getDefault())
        try{
            val address = location?.latitude?.let { coder.getFromLocation(it, location.longitude, 1) }
            val editor = activity?.getSharedPreferences("App_Preferences",Context.MODE_PRIVATE)?.edit()?.putString("Location",address?.get(0)?.locality.toString())
            editor?.commit()
            editor?.apply()
            val council = address?.get(0)?.locality.toString().lowercase()
            getCouncilInformation(council)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun startUpdate(){
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            }
        }else{
            val locationRequest = LocationRequest()
            locationRequest.interval = 200
            locationRequest.fastestInterval = 100
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            client.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startUpdate();
            }
        }
    }

    private fun getCouncilInformation(council: String?){
        if(!council.isNullOrEmpty()){
            load(council)
        }
    }

    private fun load(council: String?){
        if(!council.isNullOrEmpty()){
            Fuel.get(Links.getLink("Location",council)).responseJson { _, _, result ->
                try{
                    val councilParser = Klaxon()
                    selectedCouncil = councilParser.parse<Council>(result.get().obj().toString())
                    if(selectedCouncil?.Areas?.size!! > 0){
                        client.removeLocationUpdates(locationCallback)
                        selectedCouncil?.save(activity?.getSharedPreferences("App_Preferences", Context.MODE_PRIVATE))
                        val areaFrag = AreaFragment()
                        val data = Bundle()
                        data.putString("CouncilName", council)
                        areaFrag.arguments = data
                        parentFragmentManager.beginTransaction()
                            .replace(this.id,areaFrag)
                            .commit()
                        activity?.fragmentManager?.popBackStack();
                    }else{
                        Toast.makeText(context, "Unable to get council", Toast.LENGTH_LONG).show()
//                        val intent = Intent(this, EnterCouncilActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
//                        startActivity(intent)
                        client.removeLocationUpdates(locationCallback)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(context, "Unable to get council", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this, EnterCouncilActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP;
//                    startActivity(intent)
                    client.removeLocationUpdates(locationCallback)
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
         * @return A new instance of fragment NewLocationFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewLocationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}