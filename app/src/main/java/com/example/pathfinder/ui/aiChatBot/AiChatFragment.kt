package com.example.pathfinder.ui.aiChatBot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.pathfinder.data.models.Message
import com.example.pathfinder.data.models.User
import com.example.pathfinder.databinding.FragmentAiChatBinding

class AiChatFragment : Fragment() {

    lateinit var sendBtn: ImageButton
    lateinit var editText: EditText
    lateinit var messagesList: MessagesList
    lateinit var us: User
    lateinit var chatgpt: User
    lateinit var adapter: MessagesListAdapter<Message>
    lateinit var loadingBar: ProgressBar
    private lateinit var binding: FragmentAiChatBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ai_chat, container, false)


        sendBtn = binding.imageButton2
        loadingBar = binding.loadingBar
        editText = binding.editTextTextPersonName2
        messagesList = binding.messagesList2

        var imageLoader: ImageLoader = object : ImageLoader {
            override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
            }
        }
        adapter = MessagesListAdapter<Message>("1", imageLoader)
        messagesList.setAdapter(adapter)

        us = User("1", "jsh", "")
        chatgpt = User("2", "ChatGPT", "")

        sendBtn.setOnClickListener {
            var message: Message = Message("m1", editText.text.toString(), us, Calendar.getInstance().time)
            adapter.addToStart(message, true)
            performAction(editText.text.toString())
            editText.text.clear()
        }

        return binding.root
    }

    fun performAction(input: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openai.com/v1/chat/completions"
        loadingBar.isVisible = true

        val jsonObject = JSONObject()
        val jsonArray = JSONArray("[{\"role\": \"user\", \"content\": \"$input\"}]")
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-4")
        jsonObject.put("max_tokens", 4096)
        Log.d("NetworkRequest", "Request: $jsonArray")
        val stringRequest = object : JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->
                loadingBar.isVisible = false
                var answer = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content")
                var message = Message(
                    "M2",
                    answer.trim { it <= ' ' },
                    chatgpt,
                    Calendar.getInstance().time
                )
                adapter.addToStart(message, true)
            },
            Response.ErrorListener {

                Response.ErrorListener { loadingBar.isVisible = false } // 오류가 발생하면 로딩바를 숨김
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                var map = HashMap<String, String>()
                map.put("Content-Type", "application/json")
                map.put("Authorization", "Bearer ")
                Log.d("NetworkHeaders", "Headers: $map")
                return map
            }
        }


        queue.add(stringRequest)
        Log.d("NetworkRequest", "Request: $stringRequest")
    }
}
