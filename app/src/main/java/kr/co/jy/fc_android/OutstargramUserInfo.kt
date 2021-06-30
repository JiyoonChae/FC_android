package kr.co.jy.fc_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.jy.fc_android.databinding.ActivityOutstargramUserInfoBinding

class OutstargramUserInfo : AppCompatActivity() {

    private lateinit var binding : ActivityOutstargramUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutstargramUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.allList.setOnClickListener {
            startActivity(Intent(this,OutStargramPostListActivity::class.java))
        }
        binding.showMy.setOnClickListener {
            startActivity(Intent(this, OutstargramMyPostListActivity::class.java))
        }
        binding.showUpload.setOnClickListener {
            startActivity(Intent(this,OutstargramUploadActivity::class.java))
        }

        binding.logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("login_sp", null)
            editor.commit()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}