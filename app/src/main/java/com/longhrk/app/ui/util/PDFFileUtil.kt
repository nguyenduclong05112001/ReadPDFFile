package com.longhrk.app.ui.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object PDFFileUtil {
    suspend fun retrievePDFFromUrl(fileName: String): InputStream? {
        val inputStream = CoroutineScope(Dispatchers.IO).async {
            try {
                val url = URL(fileName)
                val urlConnection: HttpURLConnection =
                    withContext(Dispatchers.IO) {
                        url.openConnection()
                    } as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    BufferedInputStream(urlConnection.inputStream)
                } else null
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        return inputStream.await()
    }
}