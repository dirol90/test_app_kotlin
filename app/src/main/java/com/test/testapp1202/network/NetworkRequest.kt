package com.test.testapp1202.network

import android.accounts.NetworkErrorException
import com.test.testapp1202.model.Film
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL: String = "http://test.php-cd.attractgroup.com/test.json"

class Api {

    fun getDataFromApi(): List<Film> {
        val apiUrl = URL(BASE_URL)
        try {
            with(apiUrl.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()

                    println("Response : $response")

                    val ja = JSONArray(response.toString())
                    val list = mutableListOf<Film>()
                    for (i in 0 until ja.length()) {
                        val jsonObject: JSONObject = ja.getJSONObject(i)
                        val itemId: String = jsonObject.getString("itemId")
                        val name: String = jsonObject.getString("name")
                        val image: String = jsonObject.getString("image")
                        val description: String = jsonObject.getString("description")
                        val time: String = jsonObject.getString("time")
                        list.add(Film(itemId.toInt(), name, image, description, time.toLong()))
                    }

                    return list
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

}