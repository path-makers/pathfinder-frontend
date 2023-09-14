package com.example.pathfinder.pages.board.write

import android.widget.ImageView
import com.example.pathfinder.pages.board.BoardModel


class BoardWriteController {

    private val model = BoardWriteModel()

    fun saveBoardData(board: BoardModel, key: String) {
        model.saveBoardData(board, key)
    }

    fun uploadImageData(key: String, imageView: ImageView) {
        model.uploadImageData(key, imageView)
    }
}
