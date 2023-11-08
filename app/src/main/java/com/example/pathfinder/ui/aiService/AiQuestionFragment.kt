package com.example.pathfinder.ui.aiService

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentAiQuestionBinding
import org.json.JSONArray
import org.json.JSONObject


import com.android.volley.Request
import com.android.volley.Response

import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest

import java.util.*
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RetryPolicy


class AiQuestionFragment : Fragment() {


    private lateinit var binding: FragmentAiQuestionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAiQuestionBinding.inflate(inflater, container, false)

        binding.btnShowResults.setOnClickListener {
            collectResponsesAndShowResults()
        }

        return binding.root
    }

    private fun collectResponsesAndShowResults() {
        val contentBuilder = StringBuilder()
        val questions = resources.getStringArray(R.array.questions)
        val responses = arrayOf(
            binding.ratingQuestion1.rating.toInt(),
            binding.ratingQuestion2.rating.toInt(),
            binding.ratingQuestion3.rating.toInt(),
            binding.ratingQuestion4.rating.toInt(),
            binding.ratingQuestion5.rating.toInt(),
            binding.ratingQuestion6.rating.toInt(),
            binding.ratingQuestion7.rating.toInt(),
            binding.ratingQuestion8.rating.toInt(),
            binding.ratingQuestion9.rating.toInt(),
            binding.ratingQuestion10.rating.toInt(),

        )


        for (i in responses.indices) {
            contentBuilder.append("${questions[i]}에 대한 점수는 ${responses[i]}.")
        }
        val mbtiType = binding.editTextMBTI.text.toString().trim()
        val wordType = binding.editTextWord.text.toString().trim()
        contentBuilder.append("그리고 제 MBTI는 $mbtiType 이고, ")
        contentBuilder.append("저를 한단어로 표현하면 $wordType 입니다.")

        contentBuilder.append("흥미도를 1부터 5로 평가했어요. 이 정보들을 바탕으로 내게 어울리는 직업을 추천해주세요.")


        val bundle = Bundle()
        bundle.putString("userResponses", contentBuilder.toString())
        Log.d("AiQuestionFragment", "userResponses: ${bundle.getString("userResponses")}")

        findNavController().navigate(R.id.action_aiQuestionFragment_to_chatFragment, bundle)
    }


//    fun sendOpenAIRequest(responses: List<Int>) {
//        val queue = Volley.newRequestQueue(requireContext())
//        val url = "https://api.openai.com/v1/chat/completions"
//
//
//
//        val contentBuilder = StringBuilder()
//        val questions = resources.getStringArray(R.array.questions)
//        for (i in responses.indices) {
//            contentBuilder.append("${questions[i]}에 대한 점수는 ${responses[i]}.")
//        }
//
//
//        contentBuilder.append("흥미도를 1부터 5로 평가했어요. 이 정보를 바탕으로 내게 어울리는 직업을 추천해주세요.")
//
//        val userResponses = JSONObject().apply {
//            put("role", "user")
//            put("content", contentBuilder.toString())
//        }
//
//        val jsonArray = JSONArray().apply {
//            put(userResponses)
//        }
//        val jsonObject = JSONObject().apply {
//            put("messages", jsonArray)
//            put("model", "gpt-4")
//            put("max_tokens", 4096)
//        }
//        Log.d("NetworkRequest", "Request: $jsonArray")
//
//
//
//
//        val stringRequest = object : JsonObjectRequest(
//            Request.Method.POST, url, jsonObject,
//            Response.Listener<JSONObject> { response ->
//                Log.d("NetworkSuccess", "Response: $response")
//                displayResults(response)
//            },
//            Response.ErrorListener { error ->
//                Log.e("NetworkError", "Error: ${error.toString()}")
//
//
//            }
//        ) {
//            override fun getHeaders(): MutableMap<String, String> {
//                var map = HashMap<String, String>()
//                map.put("Content-Type", "application/json")
//                map.put("Authorization", "Bearer ")
//                Log.d("NetworkHeaders", "Headers: $map")
//                return map
//            }
//        }
//        stringRequest.retryPolicy = object : RetryPolicy {
//            override fun getCurrentTimeout(): Int {
//                return 60000
//            }
//
//            override fun getCurrentRetryCount(): Int {
//                return 15
//            }
//
//            override fun retry(error: VolleyError?) {
//            }
//        }
//        queue.add(stringRequest)
//        Log.d("NetworkRequest", "Request: $stringRequest")
//
//    }
//
//    private fun displayResults(response: JSONObject) {
//        val answer = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
//        Log.d("NetworkAnswer", "Answer: $answer")
//
//    }




}
