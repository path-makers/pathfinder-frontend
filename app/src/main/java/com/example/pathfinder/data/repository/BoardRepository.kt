package com.example.pathfinder.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.utils.FBAuth
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class BoardRepository(private val context: Context) { // Context 추가

    fun getBoardDataByType(boardType: String, success: (List<Board>) -> Unit, error: (String) -> Unit) {
        val url = "http://138.2.114.130:8080/api/board/all?boardType=$boardType"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val boardsArray = response.getJSONArray("boards")

                val boardDataList = mutableListOf<Board>()

                for (i in 0 until boardsArray.length()) {
                    val item = boardsArray.getJSONObject(i)
                    val tagsList = mutableListOf<String>()
                    item.getJSONArray("tags").let {
                        for (j in 0 until it.length()) {
                            tagsList.add(it.getString(j))
                        }
                    }



                    val board = Board(
                        author = if (item.getString("author") == "null") "익명 유저" else item.getString("author"),
                        id = item.getString("id"),
                        title = item.getString("title"),
                        content = item.getString("content"),
                        uid = item.getString("uid"),
                        date = item.optString("createdAt", "Unknown"),
                        boardType = item.getString("boardType"),
                        tags = tagsList,

                    )


                    boardDataList.add(board)
                }

                success(boardDataList)
            },
            { err ->
                Log.w(TAG, "Error: ${err.message}")
                error(err.message ?: "Unknown error")
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }


    fun getBoardDataById(boardId: String, success: (Board) -> Unit, error: (String) -> Unit) {
        val url = "http://138.2.114.130:8080/api/board/single/$boardId"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val boardJson = response.getJSONObject("board")

                // Directly parse the board details from the response
                val tagsList = mutableListOf<String>()
                val tagsArray = boardJson.optJSONArray("tags")
                if (tagsArray != null) {
                    for (j in 0 until tagsArray.length()) {
                        tagsList.add(tagsArray.getString(j))
                    }
                }

                val commentsList = mutableListOf<Comment>()
                val commentsArray = boardJson.optJSONArray("comments")
                if (commentsArray != null) { // commentsArray가 null이 아닐 때만 반복문 실행
                    for (j in 0 until commentsArray.length()) {
                        val commentItem = commentsArray.getJSONObject(j)
                        val author = if (commentItem.getString("author") == "null") "익명 유저" else commentItem.getString("author")
                        val content = commentItem.getString("content")
                        val uid = commentItem.getString("uid")
                        val createdAt = commentItem.optLong("createdAt")

                        commentsList.add(Comment(author,content, uid, createdAt))
                    }
                }

                val board = Board(
                    author = if (boardJson.getString("author") == "null") "익명 유저" else boardJson.getString("author"),
                    id = boardJson.getString("id"),
                    title = boardJson.getString("title"),
                    content = boardJson.getString("content"),
                    uid = boardJson.getString("uid"),
                    date = boardJson.optString("createdAt", "Unknown"),
                    boardType = boardJson.getString("boardType"),
                    tags = tagsList,
                    comments = commentsList
                )

                success(board) // Invoke success callback with a single Board object
            },
            { err ->
                Log.w(TAG, "Error: ${err.message}")
                error(err.message ?: "Unknown error")
            }
        )

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }


    fun getBoardDataByAlgorithm(userId: String, success: (List<Board>) -> Unit, error: (String) -> Unit) {
        val url = "http://138.2.114.130:8080/api/board/recommend/$userId"

        val requestBody = JSONObject()
        requestBody.put("uid", userId)
        Log.d(TAG, "getBoardDataByAlgorithm: $requestBody")

        val jsonObjectRequest = CustomJsonObjectRequest(
            Request.Method.GET, url, requestBody,
            { response ->
                val boardsArray = response.getJSONArray("boards")

                val boardDataList = mutableListOf<Board>()

                for (i in 0 until boardsArray.length()) {
                    val item = boardsArray.getJSONObject(i)
                    val tagsList = mutableListOf<String>()
                    item.getJSONArray("tags").let {
                        for (j in 0 until it.length()) {
                            tagsList.add(it.getString(j))
                        }
                    }



                    val board = Board(
                        author = if (item.getString("author") == "null") "익명 유저" else item.getString("author"),
                        id = item.getString("id"),
                        title = item.getString("title"),
                        content = item.getString("content"),
                        uid = item.getString("uid"),
                        date = item.optString("createdAt", "Unknown"),
                        boardType = item.getString("boardType"),
                        tags = tagsList,

                        )

                    Log.d(TAG, "getBoardDataByAlgorithm: $board")
                    boardDataList.add(board)
                    Log.d(TAG, "getBoardDataByAlgorithm: $boardDataList")
                }

                success(boardDataList)
            },
            { err ->
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
            put("author", FBAuth.getUserName())
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



        val jsonBody = JSONObject().apply {
            put("uid", comment.uid)
            put("content", comment.content)
            put("author", FBAuth.getUserName())

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

    class CustomJsonObjectRequest(
        method: Int,
        url: String,
        private val requestBody: JSONObject?,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) : JsonObjectRequest(method, url, null, listener, errorListener) {

        override fun getBody(): ByteArray {
            return requestBody?.toString()?.toByteArray(Charset.forName("UTF-8")) ?: super.getBody()
        }
    }



}
