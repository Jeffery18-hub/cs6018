package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.lab1.databinding.ActivityMainBinding
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var  binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button1.setOnClickListener(clickListener);
        binding.button2.setOnClickListener(clickListener);
        binding.button3.setOnClickListener(clickListener);
        binding.button4.setOnClickListener(clickListener);
        binding.button5.setOnClickListener(clickListener);

        setContentView(binding.root)
    }

    private val clickListener = View.OnClickListener() {
        if(it is Button) {
            val buttonTxt = it.text.toString()
            val buttonId = it.id.toString()
//            Log.e("ID", buttonTxt)
            val intent = Intent(this, SubMainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("thisText", buttonTxt)
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }


}