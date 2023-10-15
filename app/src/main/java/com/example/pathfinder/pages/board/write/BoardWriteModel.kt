package com.example.pathfinder.pages.board.write


import android.content.Context
import com.example.pathfinder.utils.FBRef

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.pages.board.BoardModel
import org.json.JSONArray
import org.json.JSONObject


class BoardWriteModel {


    fun sendBoardData(board: BoardModel, context: Context) {
        val url = "http://138.2.114.130:8080/api/board"

        val jsonBody = JSONObject()
        jsonBody.put("uid", board.uid)
        jsonBody.put("tags", JSONArray(board.tags))
        jsonBody.put("boardType", board.boardType)
        jsonBody.put("title", board.title)
        jsonBody.put("content", board.content)

        val requestBody = jsonBody.toString()

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                // handle success response
            },
            Response.ErrorListener {
                // handle error
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charsets.UTF_8)
            }
        }

        Volley.newRequestQueue(context).add(stringRequest) 
    }


}
