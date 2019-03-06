package org.example.emwu.test1

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import java.lang.Exception
import java.net.URL

private const val TAG = "DownloadData"

class DownloadData(val listener: OnDownloadComplete): AsyncTask<String, Void, String>() {

    interface OnDownloadComplete {
        fun OnDownloadComplete(text: String)
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, ".onPostExecute start")
        listener.OnDownloadComplete(result)
    }

    override fun doInBackground(vararg params: String?): String {
        Log.d(TAG, ".doInBackground start")
        var data = ""
        try {
            data = URL(params[0]).readText()
            println(data)
        } catch (e: Exception) {
            e.printStackTrace().toString()
            Log.e(TAG, "error downloading data")
            cancel(true)
        }
        return data
    }
}