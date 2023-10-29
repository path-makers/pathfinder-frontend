package com.example.pathfinder.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.data.models.Board
import com.example.pathfinder.data.models.Comment
import org.json.JSONArray
import org.json.JSONObject

class BoardRepository(private val context: Context) { // Context 추가

    fun getFBBoardData(boardType: String, success: (List<Board>) -> Unit, error: (String) -> Unit) {
        val url = "http://138.2.114.130:8080/api/board/all?boardType=$boardType"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val boardsArray = response.getJSONArray("boards")
                Log.d(TAG, "Received data: $response")
                val boardDataList = mutableListOf<Board>()

                for (i in 0 until boardsArray.length()) {
                    val item = boardsArray.getJSONObject(i)
                    val tagsList = mutableListOf<String>()
                    item.getJSONArray("tags").let {
                        for (j in 0 until it.length()) {
                            tagsList.add(it.getString(j))
                        }
                    }

                    val commentsList = mutableListOf<Comment>()
                    item.getJSONArray("comments").let {
                        for (j in 0 until it.length()) {
                            val commentItem = it.getJSONObject(j)
                            val content = commentItem.getString("content")
                            val uid = commentItem.getString("uid")
                            val createdAt = commentItem.optString("createdAt", "Unknown")

                            commentsList.add(Comment(content, uid, createdAt))
                        }
                    }

                    val board = Board(
                        id = item.getString("id"),
                        title = item.getString("title"),
                        content = item.getString("content"),
                        uid = item.getString("uid"),
                        date = item.optString("createdAt", "Unknown"),
                        boardType = item.getString("boardType"),
                        tags = tagsList,
                        comments = commentsList
                    )

                    boardDataList.add(board)
                }

                success(boardDataList)
            },
            Response.ErrorListener { err ->
                Log.w(TAG, "Error: ${err.message}")
                error(err.message ?: "Unknown error")
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }


    fun sendBoardData(board: Board) {
        val url = "http://138.2.114.130:8080/api/board"

        val jsonBody = JSONObject().apply {
            put("uid", board.uid)
            put("tags", JSONArray(board.tags))
            put("boardType", board.boardType)
            put("title", board.title)
            put("content", board.content)
        }
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
                return hashMapOf("Content-Type" to "application/json; charset=utf-8")
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charsets.UTF_8)
            }
        }

        Volley.newRequestQueue(context).add(stringRequest)
    }




    fun sendCommentData(comment: Comment, boardId: String, callback: (Boolean) -> Unit) {

        val url = "http://138.2.114.130:8080/api/board/comment/$boardId"
        Log.d("sendCommentData", "url: $url")


        val jsonBody = JSONObject().apply {
            put("uid", comment.uid)
            put("content", comment.content)
        }
        val requestBody = jsonBody.toString()


        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                callback(true)
            },
            Response.ErrorListener {
                callback(false)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf("Content-Type" to "application/json; charset=utf-8")
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charsets.UTF_8)
            }
        }

        Volley.newRequestQueue(context).add(stringRequest)
    }


}
