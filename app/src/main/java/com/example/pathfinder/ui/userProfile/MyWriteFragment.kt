package com.example.pathfinder.ui.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pathfinder.R

class MyWriteFragment : Fragment() {

    private lateinit var tv_one: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_write, container, false)
        tv_one = view.findViewById(R.id.tv_one)
        return view
    }
}