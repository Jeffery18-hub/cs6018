package com.example.customviewdemo

import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.customviewdemo.databinding.FragmentDrawBinding


class DrawFragment : Fragment() {

        private val viewModel: SimpleViewModel by activityViewModels()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val binding = FragmentDrawBinding.inflate(inflater)

            binding.customView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        viewModel.addPoint(PointF(event.x, event.y))
                        true
                    }
                    else -> false
                }
            }

            viewModel.points.observe(viewLifecycleOwner) { points ->
                binding.customView.drawPoints(points)
            }

            return binding.root
        }
    }