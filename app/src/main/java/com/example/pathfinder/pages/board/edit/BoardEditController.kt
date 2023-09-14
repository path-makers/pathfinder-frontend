package com.example.pathfinder.pages.board.edit

import com.example.pathfinder.pages.board.BoardModel

class BoardEditController(private val view: BoardEditView) {

    private val model = BoardEditModel()

    fun editBoardData(key: String, title: String, content: String) {
        model.editBoardData(key, title, content)
    }

    fun getBoardData(key: String, onDataReceived: (BoardModel?) -> Unit) {
        model.getBoardData(key, onDataReceived)
    }

    fun getImageData(key: String, onImageReceived: (String?) -> Unit) {
        model.getImageData(key, onImageReceived)
    }
}
