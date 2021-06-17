package kr.co.jy.fc_android

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MasterApplication : Application() {
    lateinit var service : RetrofitService

    //Application을 상속받으면 Activity보다 상위 개념이기때문에 먼저 실행됨.
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        createRetrofit()

    }

    //retrofit
    fun createRetrofit() {
        //header설정
        val header = Interceptor {
            val original = it.request()

            if(checkIsLogin()){
                getUserToken()?.let {token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token " + token)
                        .build()
                    it.proceed(request)
                }

            }else{
                it.proceed(original)
            }


        }

        //client  생성
       val client = okhttp3.OkHttpClient.Builder()
           .addInterceptor(header)
           .addNetworkInterceptor(StethoInterceptor())
           .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        this.service = retrofit.create(RetrofitService::class.java)
    }

    //login했으면 header 필요, 안했으면 header 필요x
    fun checkIsLogin() :Boolean{
        //가입을하면 사용자 token을 받아서 sharedpreference에 저장이 됨. token의 여부로 로그인 판단
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if(token != "null") return true
        else return false
    }

    fun getUserToken():String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if(token=="null") return null
        else return token
    }
}