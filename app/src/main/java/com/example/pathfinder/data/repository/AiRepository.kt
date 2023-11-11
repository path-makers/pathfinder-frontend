package com.example.pathfinder.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class AiRepository(val context: Context) {

    fun getResponseFromAPI(input: String, success: (String) -> Unit, failure: () -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.openai.com/v1/chat/completions"

        val jsonObject = JSONObject()
        val jsonArray = JSONArray("[{\"role\": \"user\", \"content\": \"$input\"}]")
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-4")
        jsonObject.put("max_tokens", 4096)

        val stringRequest = object : JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->
                val answer = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content")
                success(answer)
            },
            Response.ErrorListener {
                failure()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Content-Type"] = "application/json"
                map["Authorization"] = "Bearer "
                return map
            }
        }

        queue.add(stringRequest)
    }
}
