package com.example.fragmenttester.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fragmenttester.databinding.FragmentNotificationsBinding
import com.google.zxing.integration.android.IntentIntegrator

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
            textView.text = arguments?.getString("OFB")
        })
        val integrator = IntentIntegrator.forSupportFragment(this@NotificationsFragment)
        integrator.setBeepEnabled(false)
        integrator.setCameraId(0)
        integrator.setPrompt("SCAN BARCODE")
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents !== null) {
            Toast.makeText(context,result.contents.toString(),Toast.LENGTH_LONG).show()
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}