package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lab1.databinding.ActivityMainBinding
import com.example.lab1.databinding.ActivitySubBinding

class SubMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var  binding = ActivitySubBinding.inflate(layoutInflater)
        val bundles = intent.extras!! // not null
        val btnTxt = bundles.getString("thisText")
//        Log.e("here", btnTxt.toString())
        binding.textView.text = btnTxt

        setContentView(binding.root)
    }
}