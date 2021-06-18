package kr.co.jy.fc_android

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field ("username")username:String,
        @Field("password1")password1:String,
        @Field("password2")password2:String
    ):Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field ("username")username: String,
        @Field ("password")password:String
    ):Call<User>

}