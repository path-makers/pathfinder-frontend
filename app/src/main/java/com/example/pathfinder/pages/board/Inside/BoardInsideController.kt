package com.example.pathfinder.pages.board.Inside
import com.example.pathfinder.pages.board.BoardModel
import com.example.pathfinder.pages.comment.CommentModel


class BoardInsideController(private val view: BoardInsideView) {

    private val model = BoardInsideModel()

    fun getBoardData(key: String, onResult: (BoardModel?) -> Unit) {
        model.getBoardData(key, onResult)
    }

    fun getCommentData(key: String, onResult: (List<CommentModel>) -> Unit) {
        model.getCommentData(key, onResult)
    }

    fun insertComment(key: String, comment: CommentModel) {
        model.insertComment(key, comment)
    }

    fun removeBoard(key: String) {
        model.removeBoard(key)
    }

}

