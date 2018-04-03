package com.m2mobi.markymark

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FileDownloadService {

    @GET()
    fun downloadFile(@Url fileUrl: String): Call<ResponseBody>
}