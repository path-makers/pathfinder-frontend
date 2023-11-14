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
import com.example.pathfinder.data.models.Dm
import com.example.pathfinder.data.models.DmUser
import com.example.pathfinder.data.models.Message
import com.example.pathfinder.data.models.User
import com.example.pathfinder.databinding.ActivityBoardChatBinding
import com.example.pathfinder.utils.FBAuth
import com.google.firebase.firestore.FirebaseFirestore


class BoardChatActivity:AppCompatActivity() {

    private lateinit var sendButton: ImageView
    private lateinit var messageInput: EditText
    private lateinit var messagesListView: MessagesList
    private lateinit var currentUser: DmUser
    private lateinit var recipientUser: DmUser
    private lateinit var messagesAdapter: MessagesListAdapter<Dm>
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var binding: ActivityBoardChatBinding
    private lateinit var recipientUserId: String
    private lateinit var chatRoomId: String
    private lateinit var author: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_chat)


        currentUser = DmUser(FBAuth.getUid(), "CurrentUser", "")
        recipientUserId = intent.extras?.getString("userId")?:""
        chatRoomId = intent.extras?.getString("chatRoomId") ?: ""
        author = intent.extras?.getString("author")?:""

        if (chatRoomId.isEmpty() && recipientUserId != null) {
            // 게시글에서 DM 시작 시나리오
            chatRoomId = createChatRoomId(currentUser.id, recipientUserId)
            checkAndCreateChatRoom(chatRoomId, listOf(currentUser.id, recipientUserId))
        }

        initializeUI()
        loadMessagesFromFirestore()
    }

    private fun checkAndCreateChatRoom(chatRoomId: String, participants: List<String>) {
        val db = FirebaseFirestore.getInstance()
        val chatRoomRef = db.collection("chatRooms").document(chatRoomId)

        chatRoomRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val newChatRoom = hashMapOf(
                    "participants" to participants
                    // 필요한 경우 여기에 추가 메타데이터를 추가할 수 있습니다.
                )
                chatRoomRef.set(newChatRoom)
            }
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error checking/creating chat room", e)
        }
    }
    private fun initializeUI() {
        sendButton = binding.aiBtn
        messageInput = binding.aiInputText
        messagesListView = binding.messagesList2
        loadingIndicator = binding.loadingBar

        val imageLoader = ImageLoader { imageView, _, _ -> imageView.setImageResource(R.drawable.ic_robot) }
        messagesAdapter = MessagesListAdapter("1", imageLoader)
        messagesListView.setAdapter(messagesAdapter)

        currentUser = DmUser("1", "CurrentUser", "")
        recipientUser = DmUser("2", "recipientUser", "drawable://ic_profildefault")

        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString()
            val message = Dm(
                UUID.randomUUID().toString(),
                messageText,
                currentUser,
                Calendar.getInstance().time,
                currentUser.id,
                recipientUserId,
                chatRoomId
            )
            sendMessageToFirestore(message)
            messagesAdapter.addToStart(message, true)
            messageInput.text.clear()
        }
    }

    private fun createChatRoomId(userId1: String, userId2: String): String {
        val ids = listOf(userId1, userId2).sorted()
        return ids.joinToString("-")
    }
    private fun sendMessageToFirestore(message: Dm) {
        val db = FirebaseFirestore.getInstance()
        db.collection("chatRooms").document(chatRoomId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener { Log.d("Firestore", "Message successfully written!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing message", e) }
    }


    private fun loadMessagesFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("chatRooms").document(chatRoomId)
            .collection("messages")
            .orderBy("createdAt")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Firestore", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val messagesList = ArrayList<Dm>() // 새 메시지 목록을 저장할 리스트

                for (doc in snapshots!!) {
                    val message = doc.toObject(Dm::class.java)
                    messagesList.add(message) // 리스트에 메시지 추가
                }

                messagesAdapter.clear() // 어댑터의 기존 데이터를 클리어
                messagesAdapter.addToEnd(messagesList, true) // 새로운 메시지 목록을 어댑터에 추가
            }
    }


    }






