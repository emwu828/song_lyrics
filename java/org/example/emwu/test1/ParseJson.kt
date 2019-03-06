package org.example.emwu.test1

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "ParseJson"

class ParseJson(private val listener: onParseComplete): AsyncTask<String, Void, String>() {

    interface onParseComplete {
        fun onParseComplete(lyrics: String)
    }

    override fun doInBackground(vararg params: String?): String {
        Log.d(TAG, ".doInBackground start")
        val result = "Could not find song lyrics"
        try {
            val jsonData = JSONObject(params[0])
            val message = jsonData.getJSONObject("message")
            val body = message.getJSONObject("body")
            val lyrics = body.getJSONObject("lyrics")
            val result = lyrics.getString("lyrics_body")

        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, ".doInBackground: Error processing Json data")
            cancel(true)
        }
        return result
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, ".onPostExecute start")
        listener.onParseComplete(result)
    }
}