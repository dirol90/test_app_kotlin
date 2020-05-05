package com.test.testapp1202.helper

import android.graphics.*
import android.os.AsyncTask
import android.widget.ImageView
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageDownloader {

    var task: DownloadTask? = null
    fun setImageToImageView(iv: ImageView, imageUrl: URL) {
        task = DownloadTask(iv)
        task!!.execute(imageUrl)
    }

    fun cancelDownload() {
        task!!.cancel(true)
    }

    class DownloadTask(var iv: ImageView) : AsyncTask<URL?, Int?, List<Bitmap>>() {

        override fun doInBackground(vararg urls: URL?): List<Bitmap> {
            if (!isCancelled) {
                val count = urls.size
                var connection: HttpURLConnection? = null
                val bitmaps: MutableList<Bitmap> = ArrayList()
                for (i in 0 until count) {
                    val currentURL: URL = urls[i]!!
                    try {
                        connection = currentURL.openConnection() as HttpURLConnection
                        connection.connect()
                        val inputStream: InputStream = connection.getInputStream()
                        val bufferedInputStream = BufferedInputStream(inputStream)
                        val bmp = BitmapFactory.decodeStream(bufferedInputStream)
                        bitmaps.add(bmp)
                        publishProgress(((i + 1) / count.toFloat() * 100).toInt())
                        if (isCancelled) {
                            break
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        connection!!.disconnect()
                    }
                }
                return bitmaps
            } else {
                return ArrayList()
            }
        }


        override fun onPostExecute(result: List<Bitmap>) {
            if (!isCancelled) {
                for (i in result.indices) {
                    val bitmap = result[i]
                    iv.setImageBitmap(bitmap)
                }
            }
        }
    }
}


