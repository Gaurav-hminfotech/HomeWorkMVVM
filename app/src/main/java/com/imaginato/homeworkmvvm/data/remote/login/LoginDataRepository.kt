package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.await


class LoginDataRepository constructor(
    private var api: LoginApi
) : LoginRepository {
    companion object {

        fun getHeaders(): HashMap<String, String> {
            val headers = HashMap<String, String>()
            headers["IMSI"] = "357175048449937"
            headers["IMEI"] = "510110406068589"
            return headers
        }

        const val key = "eyJhbGciOiJSUzUxMiJ9.eyJDUE4iOiJZIiwibGFzdExvZ2luIjoiMDMgQXByaWwg\n" +
                "MjAxOCAxOTozODoxMyIsImxsZXYiOiJIbWFjU0hBNTEyIiwiaXAiOiI5Mi4zOC4xM\n" +
                "zAuMTAwIiwiaXNzIjoiYmUqKipvIiwiZm4iOiJCcm8gQmVydGhvIiwiZmNpcyI6Ilki\n" +
                "LCJsb2dpbiI6IiIsImZ0IjoiWSIsImZmbiI6Ik4iLCJzZCI6IjE1MjI4MDU0OTA3NTgiL\n" +
                "CJwbG4iOiJKR2dMTUlFOHBwcktaRFZJejB6OUZBPT0iLCJmZnQiOiJOIiwidXNydH\n" +
                "AiOiJOIiwic2siOiJmMDVjMmRkOGZkYTYzNzYyYmE2NzUwYmQ3OTc3ZTQ0Y2Q\n" +
                "wM2ZiOTBkNzY3MDU3NDZhZjk4YjgxOGMyMzBhZjIzIiwiZXhwIjoxNTIyODA2M\n" +
                "DkwLCJqdGkiOiI3NDlhZDU4Ni01ZTAyLTRmNzItOWE0MC0wYTk4ODBmYzlkNG\n" +
                "MiLCJmdGwiOiJOIn0.Buqs4t\n" +
                "hUnkAh2v3okFKk8JsJ6V6XEcCpU__BaYNgj7Q8plXEJE1728FL05UvU4DRKO6GFa\n" +
                "gUF9MGx2rqO1Fh-viropeVTKu43zyIpfRqi2d4KhwA\n" +
                "sEQK7_V5sV08bKBgdIwxY9LUfKE5MOIr33Q2i8gZIcUZCG2SL8SmZX3YOe6CEwt\n" +
                "WH9Mp4hoUvo0MNhFSwR8inA1YPsm5TqrCQwj05-3FdhH6lm57CvF8uJOzBE\n" +
                "TGxeGaXs0BjN3a4o5lev4qWAa3nS\n" +
                "KEQye3IAzrvyeMNTNKA1KsNsIqgVb3ODrI8yXcPvuTN\n" +
                "YcV9K9JiMaUKNoL_0OV9THBFwHpbUQqw"
    }

    /**
     * getLoginData
     * @param loginRequest model which contains login credentials
     */

    override suspend fun getLoginData(loginRequest: LoginRequest) = flow {
        val response = api.login(loginRequest, getHeaders()).await()
        emit(response)
    }.flowOn(Dispatchers.IO)
}