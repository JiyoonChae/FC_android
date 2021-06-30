package kr.co.jy.fc_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kr.co.jy.fc_android.databinding.ActivityOutstargramUploadBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OutstargramUploadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOutstargramUploadBinding

    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutstargramUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPictures.setOnClickListener {
            getPicture()
        }

        binding.uploadPictures.setOnClickListener {
            uploadPost()
        }

        binding.showInfo.setOnClickListener {
            startActivity(Intent(this,OutstargramUserInfo::class.java))
        }
        binding.showMy.setOnClickListener {
            startActivity(Intent(this, OutstargramMyPostListActivity::class.java))
        }
        binding.allList.setOnClickListener {
            startActivity(Intent(this,OutStargramPostListActivity::class.java))
        }
    }

    fun getPicture(){
        //갤러리에서 사진가져오기!
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000){
            //uri는 자료data의 위치. 경로
            val uri : Uri = data!!.data!!
            filePath = getImageFilePath(uri)
            Log.d("path", "path : ${filePath}")
        }
    }

    //절대 경로를 찾는 함수
    fun getImageFilePath(contentUri:Uri) :String{
        var columnIndex =0   //인덱스를 적기위한 변수
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    fun uploadPost(){
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"),getContent())

        (application as MasterApplication).service.uploadPost(part,content).enqueue(
            object: Callback<Post>{
                override fun onFailure(call: Call<Post>, t: Throwable) {

                }

                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                   if (response.isSuccessful){
                       val post = response.body()
                       Log.d("path", post!!.content)
                       finish()
                       startActivity(Intent(this@OutstargramUploadActivity, OutstargramMyPostListActivity::class.java))

                   }
                }
            }
        )
    }

    fun getContent():String{
        return binding.contentInput.text.toString()
    }

}