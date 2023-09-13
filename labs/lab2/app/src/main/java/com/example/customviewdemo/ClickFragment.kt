package com.example.customviewdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.customviewdemo.databinding.FragmentClickBinding


class ClickFragment : Fragment() {


    private var buttonFunction : () -> Unit = {}
    private val viewModel: SimpleViewModel by activityViewModels()


    fun setButtonFunction(newFunc: () -> Unit) {
        buttonFunction = newFunc
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentClickBinding.inflate(inflater, container, false)
        binding.clickMe.setOnClickListener {
            buttonFunction?.invoke()
        }

        // color Spinner
        val colorAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.color_array,
            android.R.layout.simple_spinner_item
        )
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerColor.adapter = colorAdapter

        // size Spinner
        val sizeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.size_array,
            android.R.layout.simple_spinner_item
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSize.adapter = sizeAdapter

        // shape Spinner
        val shapeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.shape_array,
            android.R.layout.simple_spinner_item
        )
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerShape.adapter = shapeAdapter


        binding.spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedColor = parent.getItemAtPosition(position).toString()
                viewModel.selectColor(selectedColor)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedSize = parent.getItemAtPosition(position).toString()
                viewModel.selectSize(selectedSize)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerShape.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedShape = parent.getItemAtPosition(position).toString()
                viewModel.selectShape(selectedShape)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        return binding.root
    }


}