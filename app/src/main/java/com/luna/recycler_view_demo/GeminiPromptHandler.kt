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
        val prompt = "Generate a random news headline."

        // Build JSON body using JSONArray/JSONObject
        val part = JSONObject().put("text", prompt)
        val partsArray = JSONArray().put(part)
        val content = JSONObject().put("parts", partsArray)
        val contentsArray = JSONArray().put(content)
        val json = JSONObject().put("contents", contentsArray)

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