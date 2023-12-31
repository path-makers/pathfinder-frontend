package com.example.pathfinder.ui.aiChatBot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import androidx.fragment.app.FragmentManager
import com.example.pathfinder.data.model.Message
import com.example.pathfinder.data.model.User
import com.example.pathfinder.databinding.FragmentAiChatBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AiChatFragment : Fragment() {

    lateinit var sendBtn: ImageView
    lateinit var editText: EditText
    lateinit var messagesList: MessagesList
    lateinit var us: User
    lateinit var chatgpt: User
    lateinit var adapter: MessagesListAdapter<Message>
    lateinit var loadingBar: ProgressBar
    private lateinit var binding: FragmentAiChatBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ai_chat, container, false)



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
            var message: Message = Message("m1", editText.text.toString(), us, Calendar.getInstance().time)
            adapter.addToStart(message, true)
            performAction(editText.text.toString())
            editText.text.clear()
        }


        val userResponses = arguments?.getString("userResponses")

        if (userResponses != null) {
            performAction(userResponses)
        }

        // 뒤로가기
        val fragmentManager: FragmentManager = parentFragmentManager

        binding.buttonBack.setOnClickListener {
            repeat(2){
                clearBackStack(fragmentManager)
            }
        }

        hideBottomNavigation(true);

        return binding.root
    }

        private fun performAction(input: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openai.com/v1/chat/completions"
        loadingBar.isVisible = true

        val jsonObject = JSONObject()
        val jsonArray = JSONArray("[{\"role\": \"user\", \"content\": \"$input\"}]")
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-4")
        jsonObject.put("max_tokens", 4096)

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

                return map
            }

        }
            stringRequest.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return 60000
                }

                override fun getCurrentRetryCount(): Int {
                    return 15
                }

                override fun retry(error: VolleyError?) {
                }
            }


        queue.add(stringRequest)

    }


    private fun clearBackStack(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        hideBottomNavigation(false)
//    }

    fun hideBottomNavigation(bool: Boolean) {
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (bool)
            bottomNavigation.visibility = View.GONE
        else
            bottomNavigation.visibility = View.VISIBLE
    }
}
