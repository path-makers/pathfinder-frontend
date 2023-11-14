package com.example.pathfinder.ui.board.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pathfinder.R
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.pathfinder.data.models.Message
import com.example.pathfinder.data.models.User
import com.example.pathfinder.databinding.ActivityBoardChatBinding
import com.google.firebase.firestore.FirebaseFirestore


class BoardChatActivity:AppCompatActivity() {

    lateinit var sendBtn: ImageView
    lateinit var editText: EditText
    lateinit var messagesList: MessagesList
    lateinit var us: User
    lateinit var chatgpt: User
    lateinit var adapter: MessagesListAdapter<Message>
    lateinit var loadingBar: ProgressBar
    private lateinit var binding: ActivityBoardChatBinding
    lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_chat)
        userId = intent.extras?.get("userId").toString()

        sendBtn = binding.aiBtn
        loadingBar = binding.loadingBar
        editText = binding.aiInputText
        messagesList = binding.messagesList2

        var imageLoader =
            ImageLoader { imageView, _, _ -> imageView?.setImageResource(R.drawable.ic_robot) }
        adapter = MessagesListAdapter<Message>("1", imageLoader)
        messagesList.setAdapter(adapter)

        us = User("1", "jsh", "")
        chatgpt = User("2", "ChatGPT", "drawable://ic_robot")

        sendBtn.setOnClickListener {
            val messageText = editText.text.toString()
            val message =
                Message(UUID.randomUUID().toString(), messageText, us, Calendar.getInstance().time)
            sendMessageToFirestore(message)
            adapter.addToStart(message, true)
            editText.text.clear()
        }

        loadMessagesFromFirestore()
    }
    private fun sendMessageToFirestore(message: Message) {
        val db = FirebaseFirestore.getInstance()
        db.collection("messages")
            .add(message)
            .addOnSuccessListener { Log.d("Firestore", "Message successfully written!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing message", e) }
    }

    private fun loadMessagesFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("messages")
            .whereEqualTo("chatRoomId", chatRoomId) // chatRoomId는 현재 채팅방을 식별하는 데 사용됩니다.
            .orderBy("timestamp") // 메시지를 시간 순으로 정렬합니다.
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Firestore", "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (doc in snapshots!!) {
                    val message = doc.toObject(Message::class.java)
                    adapter.addToStart(message, false)
                }
            }
    }


    }






