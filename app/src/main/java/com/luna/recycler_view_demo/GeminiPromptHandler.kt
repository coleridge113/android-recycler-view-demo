package com.luna.recycler_view_demo

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class GeminiPromptHandler {
    private val geminiApiKey = BuildConfig.key
    private val client = OkHttpClient()

    suspend fun getRandomNews(): String? = withContext(Dispatchers.IO) {
        Log.d("GeminiPromptHandler", "getRandomNews called")
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$geminiApiKey"
//        val prompt = "Generate a random news headline with at least 10 words."
        val prompt = "Generate news banner text directly with at least 10 words. Don't say anything else."

        val json = JSONObject().put(
                "contents", JSONArray().put(
                JSONObject().put(
                    "parts", JSONArray().put(
                    JSONObject().put("text", prompt)
                    )
                )
            )
        )

        val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            Log.d("GeminiPromptHandler", response.toString())

            if (!response.isSuccessful) {
                Log.e("GeminiPromptHandler", "Error: ${response.code} - ${response.message}")
                Log.e("GeminiPromptHandler", "Error body: ${response.body?.string()}")
                return@withContext null
            }
            
            val responseBody = response.body?.string() ?: return@withContext null
            val responseJson = JSONObject(responseBody)
            Log.d("GeminiPromptHandler", responseJson.toString())
            val text = responseJson
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
            return@withContext text
        }
    }
}