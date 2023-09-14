package com.example.pathfinder.pages.board.edit

import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.utils.FBAuth
import com.example.pathfinder.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardEditModel {

    var writerUid: String = ""

    fun editBoardData(key: String, title: String, content: String) {
        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(
                    title,
                    content,
                    writerUid,
                    FBAuth.getTime()
                )
            )
    }

    fun getBoardData(key: String, onDataReceived: (BoardModel?) -> Unit) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataModel = snapshot.getValue(BoardModel::class.java)
                writerUid = dataModel!!.uid
                onDataReceived(dataModel)
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    fun getImageData(key: String, onImageReceived: (String?) -> Unit) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("$key.png")

        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onImageReceived(task.result.toString())
            } else {
                onImageReceived(null)
            }
        }
    }
}
