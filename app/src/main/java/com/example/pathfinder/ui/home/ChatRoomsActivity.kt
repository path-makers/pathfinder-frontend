package com.example.pathfinder.ui.home



import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pathfinder.data.models.ChatRoom
import com.example.pathfinder.databinding.ActivityChatRoomsBinding
import com.example.pathfinder.utils.FBAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatRoomsBinding
    private lateinit var chatRoomsAdapter: ChatRoomsAdapter
    private lateinit var chatRoomsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()
        loadChatRooms()



    }

    private fun initializeUI() {
        chatRoomsList = binding.chatRoomsRecyclerView
        chatRoomsList.layoutManager = LinearLayoutManager(this)
        chatRoomsAdapter = ChatRoomsAdapter(listOf()) // 비어 있는 목록으로 어댑터를 초기화합니다.
        chatRoomsList.adapter = chatRoomsAdapter
    }

    private fun loadChatRooms() {
        val db = FirebaseFirestore.getInstance()
        val currentUserId = getCurrentUserId() // 현재 사용자의 ID를 가져오는 함수.

        db.collection("chatRooms")
            .whereArrayContains("participants", currentUserId)
            .get()
            .addOnSuccessListener { documents ->
                val chatRooms = documents.map { it.toObject(ChatRoom::class.java) }
                chatRoomsAdapter.updateList(chatRooms) // 채팅방 목록으로 어댑터를 업데이트합니다.
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error getting chat rooms", e)
            }
    }

    // 현재 사용자의 ID를 반환하는 함수입니다. 이 함수는 앱에 맞게 구현해야 합니다.
    private fun getCurrentUserId(): String {
        // 예시: return FirebaseAuth.getInstance().currentUser?.uid ?: ""
        return FBAuth.getUid()
    }
}
