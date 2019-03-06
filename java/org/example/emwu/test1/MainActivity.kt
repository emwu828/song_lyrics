package org.example.emwu.test1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), DownloadData.OnDownloadComplete, ParseJson.onParseComplete {
    private var lyricText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate start")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val artistInput: EditText = findViewById(R.id.artistInput)
        val songInput: EditText = findViewById(R.id.songInput)
        val submitButton: Button = findViewById(R.id.submitButton)

        lyricText = findViewById(R.id.lyricText)
        lyricText?.movementMethod = ScrollingMovementMethod()

        val downloadData by lazy { DownloadData(this) }

        submitButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View) {
                Log.d(TAG, ".onClick start")
                if (artistInput.text.isNotEmpty() && songInput.text.isNotEmpty()) {
                    var url = createUri(
                        "https://api.musixmatch.com/ws/1.1/matcher.lyrics.get",
                        artistInput.text.toString(),
                        songInput.text.toString()
                    )
                    println(url)
                    downloadData.execute(url)
                } else {
                    Snackbar.make(v, "Please enter artist and song titles", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun OnDownloadComplete(text: String) {
        Log.d(TAG, ".onDownloadComplete start")
        println(text)
        ParseJson(this).execute(text)
    }

    override fun onParseComplete(lyrics: String) {
        println(lyrics)
        lyricText?.text = lyrics
    }

    fun createUri(baseURL: String, artist: String, song: String): String {
        Log.d(TAG, ".createUri start")
        var uri = Uri.parse(baseURL).buildUpon()
            .appendQueryParameter("format", "json")
            .appendQueryParameter("q_track", song)
            .appendQueryParameter("q_artist", artist)
            .appendQueryParameter("apikey", "******")
            .build()

        return uri.toString()
    }
}
