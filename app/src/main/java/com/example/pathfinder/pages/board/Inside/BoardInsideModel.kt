package com.example.pathfinder.pages.board.Inside
import android.util.Log

import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.pages.board.CommentModel

import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardInsideModel {

    fun getBoardData(key: String, onResult: (BoardModel?) -> Unit) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataModel = snapshot.getValue(BoardModel::class.java)
                onResult(dataModel)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    fun getCommentData(key: String, onResult: (List<CommentModel>) -> Unit) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val commentDataList = mutableListOf<CommentModel>()

                for (dataModel in snapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    item?.let { commentDataList.add(it) }
                }
                onResult(commentDataList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)
    }

    fun insertComment(key: String, comment: CommentModel) {
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(comment)
    }

    fun removeBoard(key: String) {
        FBRef.boardRef.child(key).removeValue()
    }

    companion object {
        private val TAG = BoardInsideModel::class.java.simpleName
    }

}
