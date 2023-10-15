package com.example.pathfinder.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.pages.board.BoardModel
import org.json.JSONObject

class BoardRepository(private val context: Context) { // Context 추가

    fun getFBBoardData(success: (List<BoardModel>) -> Unit, error: (String) -> Unit) {
        val url = "http://138.2.114.130:8080/api/board/all"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val boardsArray = response.getJSONArray("boards")
                Log.d(TAG, "Received data: $response")
                val boardDataList = mutableListOf<BoardModel>()

                for (i in 0 until boardsArray.length()) {
                    val item = boardsArray.getJSONObject(i)
                    val tagsList = mutableListOf<String>()
                    item.getJSONArray("tags").let {
                        for (j in 0 until it.length()) {
                            tagsList.add(it.getString(j))
                        }
                    }

                    val commentsList = mutableListOf<String>()
                    item.getJSONArray("comments").let {
                        for (j in 0 until it.length()) {
                            commentsList.add(it.getString(j))
                        }
                    }

                    val boardModel = BoardModel(
                        title = item.getString("title"),
                        content = item.getString("content"),
                        uid = item.getString("uid"),
                        date = item.optString("createdAt", "Unknown"),
                        boardType = item.getString("boardType"),
                        tags = tagsList,
                        comments = commentsList
                    )

                    boardDataList.add(boardModel)
                }

                success(boardDataList) // 콜백 호출
            },
            Response.ErrorListener { err ->
                Log.w(TAG, "Error: ${err.message}")
                error(err.message ?: "Unknown error") // 에러 콜백 호출
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest) // Context 사용
    }
}
