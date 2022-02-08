package com.example.interiewprepandroidapp.data.network

import com.example.interiewprepandroidapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class UserRequest {

    suspend fun fetchUserData(urlString : String) : List<User> {
        val fetchedUsers = withContext(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                val con = url.openConnection() as HttpURLConnection

                //CON PROPS
                con.requestMethod = "GET"
                con.connectTimeout = 15000
                con.readTimeout = 15000
                con.doInput = true

                if (con.responseCode >= 200 && con.responseCode < 300) {
                    val jsonString = parseOutResponse(con.inputStream)

                    val userJson = JSONArray(jsonString)
                    val allUsers = mutableListOf<User>()

                    for (i in 0 until userJson.length()) {
                        val jo = userJson.getJSONObject(i)

                        val name = jo.getString("name")
                        val username = jo.getString("username")
                        val email = jo.getString("email")

                        val user = User(name, username, email)
                        allUsers.add(user)
                    }

                    System.out.println("Num users total: ${allUsers.size}")

                    allUsers
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                // return "URL ERROR " + e.message

            } catch (e: IOException) {
                e.printStackTrace()
                // return "CONNECT ERROR " + e.message
            }

            emptyList<User>()
        }

        return fetchedUsers
    }

    private fun parseOutResponse(conInputStream : InputStream) : String {
        val inputStream = BufferedInputStream(conInputStream)

        val inputStreamReader = BufferedReader(InputStreamReader(inputStream))

        val response = StringBuilder()
        var currentLine: String?

        while (inputStreamReader.readLine().also { currentLine = it } != null) response.append(
            currentLine
        )

        inputStreamReader.close()

        return response.toString()
    }


}