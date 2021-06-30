package kr.co.jy.fc_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.jy.fc_android.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joinButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val username= binding.userIdInput.text.toString()
            val password = binding.userPwInput.text.toString()
            (application as MasterApplication).service.login(username, password).enqueue(object:
                Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {

                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful()) {
                        val user = response.body()
                        val token = user!!.token!!
                        saveUserToken(token, this@LoginActivity)
                        (application as MasterApplication).createRetrofit()
                        Toast.makeText(this@LoginActivity, "로그인 하였습니다", Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this@LoginActivity, OutStargramPostListActivity::class.java)
                        )
                    }
                    }

            })
        }
    }

    fun saveUserToken(token:String, activity:Activity){
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.commit()

    }

}