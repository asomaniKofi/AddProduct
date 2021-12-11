package com.example.fragmenttester.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fragmenttester.R
import com.example.fragmenttester.databinding.FragmentHomeBinding
import com.example.fragmenttester.ui.notifications.NotificationsFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val btn = root.findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            val newFrag = NotificationsFragment()
            val dataBundle = Bundle()
            dataBundle.putString("OFB","If you had one wish what bredrin woulkd you bring back")
            newFrag.arguments = dataBundle
            parentFragmentManager.beginTransaction()
                .replace(this.id,newFrag,"New Fragment")
                .commit()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}