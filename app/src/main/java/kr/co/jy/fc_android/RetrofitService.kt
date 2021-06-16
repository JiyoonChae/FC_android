package kr.co.jy.fc_android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("user/signup/")
    fun register(
        @Body register: Register
    ):Call<User>


}