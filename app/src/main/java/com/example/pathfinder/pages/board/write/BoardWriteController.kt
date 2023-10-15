package com.example.pathfinder.pages.board.write

import android.content.Context
import android.widget.ImageView
import com.example.pathfinder.pages.board.BoardModel


class BoardWriteController {

    private val model = BoardWriteModel()

    fun sendBoardData(board: BoardModel, context: Context) {
        model.sendBoardData(board, context)
    }


}
