package com.example.pathfinder.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.R
import com.example.pathfinder.pages.board.BoardListLVAdapter
import com.example.pathfinder.pages.board.Inside.BoardInsideView
import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.databinding.FragmentBoardBinding
import com.example.pathfinder.pages.board.write.BoardWriteView
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject


class BoardFragment : Fragment() {
    private lateinit var binding: FragmentBoardBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private val TAG = BoardFragment::class.java.simpleName

    private lateinit var boardRVAdapter: BoardListLVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)

        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter



        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(context, BoardInsideView::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)

        }


        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteView::class.java)
            startActivity(intent)
        }


        getFBBoardData()

        return binding.root
    }

    private fun getFBBoardData() {
        // URL 설정
        val url = "http://138.2.114.130:8080/api/board/all"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val boardsArray = response.getJSONArray("boards")
                Log.d(TAG, "Received data: $response")

                for (i in 0 until boardsArray.length()) {
                    val item = boardsArray.getJSONObject(i)

                    val tagsArray = item.getJSONArray("tags")
                    val tagsList = mutableListOf<String>()
                    for (j in 0 until tagsArray.length()) {
                        tagsList.add(tagsArray.getString(j))
                    }

                    val commentsArray = item.getJSONArray("comments")
                    val commentsList = mutableListOf<String>()
                    for (j in 0 until commentsArray.length()) {
                        commentsList.add(commentsArray.getString(j))
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

                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Log.w(TAG, "Error: ${error.message}")
            }
        )

        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
    }


}


