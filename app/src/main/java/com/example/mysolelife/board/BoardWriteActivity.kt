package com.example.mysolelife.board

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysolelife.R
import com.example.mysolelife.databinding.ActivityBoardWriteBinding
import com.example.mysolelife.utils.FBAuth
import com.example.mysolelife.utils.FBRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding

    private var isImageUpload = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val userEmail = FBAuth.getEmail()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            val displayName = Firebase.auth.currentUser?.displayName

            Log.d(TAG, title)
            Log.d(TAG, content)

            //파이어 베이스 store에 이미지를 저장하고 싶다
            //만약에 내가 게시글 클릭 했을 때 , 게시글에 대한 정보를 받아와야 함.
            //이미지 이름에 대한 정보를 모르기 때문에
            //이미지 이름을 문서의 key값으로 해줘서 이미지에 대한 정보를 찾기 쉽게 함.


            //이미지 넣을때 키 값으로 이미지의 이름으로 넣고 싶어서
            val key = FBRef.boardRef.push().key.toString() //키 값 먼저 생성하게 함

            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title,content,uid,time,userEmail,displayName))

            //Toast.makeText(this,"게시글 입력 완료",Toast.LENGTH_LONG).show()
            if(isImageUpload==true) {
                imageUpload(key)
            }
            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,100)
            isImageUpload = true
        }
    }

    private fun imageUpload(key:String){

        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key+".png")

        val imageView = binding.imageArea

        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== RESULT_OK && requestCode ==100){
            binding.imageArea.setImageURI(data?.data)
        }
    }
}