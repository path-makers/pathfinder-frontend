package com.example.pathfinder.ui.board

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathfinder.data.model.Board
import com.example.pathfinder.data.repository.BoardRepository
import com.example.pathfinder.ui.board.viewModel.BoardViewModel
import com.example.pathfinder.ui.board.viewModel.BoardViewModelFactory



    class BoardActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                // BoardViewModelFactory를 사용하여 ViewModel 인스턴스 생성
                val viewModel: BoardViewModel = viewModel(
                    factory = BoardViewModelFactory(
                        BoardRepository(this)
                    )
                )
                BoardScreen(viewModel)
            }
        }

        @Composable
        fun BoardScreen(viewModel: BoardViewModel) {
            val boardDataListMentor by viewModel.boardDataListMentor.observeAsState(emptyList())
            val boardDataListMentee by viewModel.boardDataListMentee.observeAsState(emptyList())
            var currentBoardType by remember { mutableStateOf("MENTOR") }

            Column {
                // 토글 버튼 UI
                Row {
                    ToggleButton(
                        text = "멘토",
                        selected = currentBoardType == "MENTOR",
                        onClick = { currentBoardType = "MENTOR" })
                    ToggleButton(
                        text = "멘티",
                        selected = currentBoardType == "MENTEE",
                        onClick = { currentBoardType = "MENTEE" })
                }
                // 리스트 표시 UI
                BoardList(boards = if (currentBoardType == "MENTOR") boardDataListMentor else boardDataListMentee)
            }
        }

        @Composable
        fun BoardList(boards: List<Board>) {
            LazyColumn {
                items(boards) { board ->
                    BoardItem(board)
                }
            }
        }

        @Composable
        fun BoardItem(board: Board) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // 태그 레이아웃
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 태그를 동적으로 표시
                    board.tags.forEach { tag ->
                        Text(
                            text = tag,
                            color = Color.Black,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                // 제목
                Text(
                    text = board.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))

                // 내용
                Text(
                    text = board.content,
                    color = Color.Black,
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))

                // 사용자 이름과 시간
                Row {
                    Text(
                        text = board.author,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = board.date,
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }


        @Composable
        fun ToggleButton(text: String, selected: Boolean, onClick: () -> Unit) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = if (selected) Color.Gray else Color.Transparent)
            ) {
                Text(text)
            }
        }

    }

