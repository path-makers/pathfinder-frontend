package com.example.pathfinder.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Common {
    companion object {
        fun formatDate(timeStamp: Long): String {
            val date = Date(timeStamp)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            return sdf.format(date)
        }

        fun formatEndDate(timeStamp: Long): String {
            val date = Date(timeStamp)
            val sdf = SimpleDateFormat("MM월 dd일 까지", Locale.getDefault())
            return sdf.format(date)
        }

    }
}