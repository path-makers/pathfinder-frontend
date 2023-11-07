package com.example.pathfinder.ui.aiService

import android.os.Bundle
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
        val responses = arrayOf(
            binding.ratingQuestion1.rating,
            binding.ratingQuestion2.rating,
            binding.ratingQuestion3.rating,
            binding.ratingQuestion4.rating,
            binding.ratingQuestion5.rating,
            binding.ratingQuestion6.rating,
            binding.ratingQuestion7.rating,
            binding.ratingQuestion8.rating,
            binding.ratingQuestion9.rating,
            binding.ratingQuestion10.rating,

        )



        findNavController().navigate(R.id.action_aiQuestionFragment_to_chatFragment)
    }


    fun sendOpenAIRequest(responses: List<Int>) {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openai.com/v1/chat/completions"

        val jsonArray = JSONArray()
        val questions = resources.getStringArray(R.array.questions) // 질문 배열 리소스
        for (i in responses.indices) {
            val userResponse = JSONObject()
            userResponse.put("role", "user")
            userResponse.put("content", "${questions[i]} ${responses[i]}.")
            jsonArray.put(userResponse)
        }

        val prompt = "이 정보를 바탕으로 내게 어울리는 직업을 추천해주세요."
        val aiRequest = JSONObject()
        aiRequest.put("role", "system")
        aiRequest.put("content", prompt)
        jsonArray.put(aiRequest)

        val jsonObject = JSONObject()
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-4")
        jsonObject.put("max_tokens", 4096)

        val stringRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->

                displayResults(response)
            },
            Response.ErrorListener { error ->


            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Authorization"] = "Bearer API"
                return headers
            }
        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        queue.add(stringRequest)
    }

    private fun displayResults(response: JSONObject) {
        val answer = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")

    }




}
