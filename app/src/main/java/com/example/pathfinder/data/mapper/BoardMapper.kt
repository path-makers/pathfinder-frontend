package com.example.pathfinder.data.mapper


import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.data.response.model.Board as ResponseBoard

fun responseBoardListModelToDataModel(
    boardList: List<ResponseBoard>
): List<Board> {
    return boardList.map { board ->
        Board(
            author = board.author?:"익명 유저",
            id = board.id,
            title = board.title,
            content = board.content,
            uid = board.uid,
            date = board.date,
            boardType = board.boardType,
            tags = board.tags.orEmpty(),

            comments = board.comments?.map { comment ->
                Comment(
                    author = comment.author?:"익명 유저",
                    content = comment.content,
                    uid = comment.uid,
                    createdAt = comment.createdAt
                )
            } ?: emptyList()
        )
    }
}