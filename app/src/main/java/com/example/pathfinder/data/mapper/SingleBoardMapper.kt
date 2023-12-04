package com.example.pathfinder.data.mapper


import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.response.model.Board as ResponseBoard

fun responseBoardModelToDataModel(
    board: ResponseBoard
): Board {
    return Board(
            author = board.author,
            id = board.id,
            title = board.title,
            content = board.content,
            uid = board.uid,
            date = board.date,
            boardType = board.boardType,
            tags = board.tags.orEmpty(),
            comments = board.comments.orEmpty().map { comment ->
                Comment(
                    author = comment.author,
                    content = comment.content,
                    uid = comment.uid,
                    createdAt = comment.createdAt
                )
            }
        )


}