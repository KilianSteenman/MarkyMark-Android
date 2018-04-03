/*
* Copyright (C) M2mobi BV - All Rights Reserved
*/

package com.m2mobi.markymark

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.m2mobi.markymarkandroid.MarkyMarkAndroid
import com.m2mobi.markymarkandroid.MarkyMarkView
import com.m2mobi.markymarkcontentful.ContentfulFlavor
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedReader


class MainActivity : AppCompatActivity() {

    private var markyMarkView: MarkyMarkView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        markyMarkView = findViewById(R.id.markymarkview)
        val markyMark = MarkyMarkAndroid.getMarkyMark(this, ContentfulFlavor(), PicassoImageLoader(this))
        markyMarkView?.setMarkyMark(markyMark)

        val markdown = loadMarkdownFromAsset(this, "contentful.txt")

        markyMarkView?.setMarkdown(markdown)

        loadMarkdownFromGithub()
    }

    private fun loadMarkdownFromAsset(pContext: Context, pFileName: String): String {
        return pContext.assets.open(pFileName).bufferedReader().use(BufferedReader::readText)
    }

    private fun loadMarkdownFromGithub() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.github.com")
                .build()

        val downloadService = retrofit.create(FileDownloadService::class.java)

        val call = downloadService.downloadFile(MARKYMARK_README)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    markyMarkView?.setMarkdown(response.body()?.string())
                    Log.d(LOG_TAG, "Readme downloaded:\n${response.body()?.string()}")
                } else {
                    Log.d(LOG_TAG, "Readme download failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(LOG_TAG, "Error loading readme", t)
            }
        })
    }

    private companion object {
        private const val LOG_TAG = "MainActivity"
        private const val MARKYMARK_README = "https://raw.githubusercontent.com/M2Mobi/MarkyMark-Android/master/README.md"
    }
}
