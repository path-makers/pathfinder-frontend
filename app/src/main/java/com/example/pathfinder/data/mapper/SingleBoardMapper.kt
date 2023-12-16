package com.example.pathfinder.data.mapper


import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.response.BoardSingleResponse


fun responseBoardModelToDataModel(
    board: BoardSingleResponse
): Board {
    return Board(
            author = board.board.author,
            id = board.board.id,
            title = board.board.title,
            content = board.board.content,
            uid = board.board.uid,
            createdAt = board.board.createdAt,
            boardType = board.board.boardType,
            tags = board.board.tags.orEmpty(),
            comments = board.board.comments.orEmpty().map { comment ->
                Comment(
                    author = comment.author,
                    content = comment.content,
                    uid = comment.uid,
                    createdAt = comment.createdAt
                )
            }
        )


}