package com.example.bottomnavigationapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentActivity(R.id.home)
                R.id.scan->setCurrentActivity(R.id.scan)
                R.id.location->setCurrentActivity(R.id.location)
            }
            true
        }
    }

    private fun setCurrentActivity(itemid:Int){
        Toast.makeText(applicationContext,itemid.toString(),Toast.LENGTH_LONG).show()
    }

}