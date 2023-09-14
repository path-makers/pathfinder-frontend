package com.example.pathfinder.pages.board.write


import com.example.pathfinder.utils.FBRef

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.example.pathfinder.pages.board.BoardModel


class BoardWriteModel {

    fun saveBoardData(board: BoardModel, key: String) {
        FBRef.boardRef
            .child(key)
            .setValue(board)
    }

    fun uploadImageData(key: String, imageView: ImageView) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child("$key.png")

        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // Handle successful uploads
        }
    }
}
