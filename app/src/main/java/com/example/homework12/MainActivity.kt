package com.example.homework12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework12.databinding.ActivityMainBinding
import com.example.homework12.ui.main.BlankFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BlankFragment.newInstance()).commitNow()
        }
    }
}