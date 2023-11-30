package com.example.pathfinder.ui.teamBuilding

import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pathfinder.R
import com.example.pathfinder.data.model.Comment
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.utils.CommentRVAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TeamBuildingInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding
    private val db = Firebase.firestore
    private val commentList = mutableListOf<Comment>()
    private lateinit var commentAdapter: CommentRVAdapter
    private lateinit var teamId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        // 메뉴
        binding.boardSettingIcon.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.menu_option, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_refresh -> {
                        // 새로고침
                        initializeUI()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }

        teamId = intent.getStringExtra("key") ?: return
        initializeUI()
    }

    private fun initializeUI() {
        binding.backButton.setOnClickListener { finish() }
        setupCommentListView()
        initCommentButton()
        fetchTeamData(teamId)
    }

    private fun fetchTeamData(teamId: String) {
        db.collection("teamBuilding").document(teamId)
            .get()
            .addOnSuccessListener { document ->
                document?.toObject(Team::class.java)?.let { updateUI(it) }
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun setupCommentListView() {
        commentAdapter = CommentRVAdapter(commentList)
        binding.commentRV.adapter = commentAdapter
        binding.commentRV.layoutManager = LinearLayoutManager(this)
        loadComments(teamId)
    }

    private fun initCommentButton() {
        binding.commentBtn.setOnClickListener {
            binding.commentArea.text.toString().takeIf { it.isNotBlank() }?.let { commentText ->
                val comment = Comment(author = getUserName(), content = commentText,createdAt = System.currentTimeMillis())
                postComment(teamId, comment)
                binding.commentArea.text.clear()
            }
        }
    }

    private fun getUserName() = Firebase.auth.currentUser?.displayName ?: "Anonymous"

    private fun postComment(teamId: String, comment: Comment) {
        db.collection("teamBuilding")
            .document(teamId)
            .collection("comments")
            .add(comment)
            .addOnSuccessListener {
                // Comment posted successfully
            }
            .addOnFailureListener {
                // Handle the error
            }
    }

    private fun loadComments(teamId: String) {
        db.collection("teamBuilding")
            .document(teamId)
            .collection("comments")
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // Handle the error
                    Log.e("loadComments", "Error loading comments", e)
                    return@addSnapshotListener
                }
                snapshot?.toObjects(Comment::class.java)?.let { comments ->
                    Log.d("loadComments", "Comments loaded: ${comments.size}")
                    commentList.clear()
                    commentList.addAll(comments)
                    commentAdapter.notifyDataSetChanged()
                }
            }
    }

    private fun updateUI(team: Team) {
        with(binding) {
            typeArea.text = team.category
            titleArea.text = team.title
            userNameArea.text = team.displayName
            timeArea.text = team.uploadTime
            textArea.text = team.content
        }
    }
}
