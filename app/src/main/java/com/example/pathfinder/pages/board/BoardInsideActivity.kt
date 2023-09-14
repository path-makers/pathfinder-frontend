package com.example.pathfinder.pages.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.pathfinder.R
import com.example.pathfinder.pages.comment.CommentLVAdapter
import com.example.pathfinder.pages.comment.CommentModel
import com.example.pathfinder.databinding.ActivityBoardInsideBinding
import com.example.pathfinder.fragments.TalkFragment
import com.example.pathfinder.pages.board.edit.BoardEditView
import com.example.pathfinder.utils.FBAuth
import com.example.pathfinder.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding

    private val commentDataList = mutableListOf<CommentModel>()
    private lateinit var commentAdapter : CommentLVAdapter

    private lateinit var key: String
    private val TAG = TalkFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }


        key = intent.getStringExtra("key").toString()

        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        getCommentData(key)

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter


    }

    fun getCommentData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in snapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)
    }

    fun insertComment(key: String) {
        val displayName = Firebase.auth.currentUser?.displayName ?: "Anonymous" // 현재 사용자의 이름을 가져옵니다

        FBRef.commentRef
            .child(key)
            .push()
            .setValue(
                CommentModel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime(),
                    displayName
                )
            )

        //Toast.makeText(this, "댓글 입력 완료", Toast.LENGTH_LONG).show()
        binding.commentArea.setText("")
    }

    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {

            //Toast.makeText(this, "수정버튼을 눌렀습니다.", Toast.LENGTH_LONG).show()

            val intent = Intent(this, BoardEditView::class.java)
            intent.putExtra("key", key).toString()
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {

            FBRef.boardRef.child(key).removeValue()
            //Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show()
            finish()

        }


    }

    private fun getImageData(key: String) {

        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {

                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {
                binding.getImageArea.isVisible = false
            }

        })
    }

    private fun getBoardData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                try {
                    val dataModel = snapshot.getValue(BoardModel::class.java)

                    binding.titleArea.text = dataModel!!.title
                    binding.textArea.text = dataModel!!.content
                    binding.timeArea.text = dataModel!!.time



                    val myUid = FBAuth.getUid()
                    val writeUid = dataModel.uid

                    if (myUid.equals(writeUid)) {
                        //  Toast.makeText(baseContext, "내가 글쓴이", Toast.LENGTH_LONG).show()
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        // Toast.makeText(baseContext, "내가 글쓴이 아님", Toast.LENGTH_LONG).show()

                    }


                } catch (e: Exception) {
                    Log.d(TAG, "삭제완료")
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }


}




