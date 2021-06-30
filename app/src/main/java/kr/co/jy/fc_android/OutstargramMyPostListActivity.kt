package kr.co.jy.fc_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kr.co.jy.fc_android.databinding.ActivityOutstargramMyPostListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutstargramMyPostListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOutstargramMyPostListBinding
    lateinit var myPostRecyclerView: RecyclerView
    lateinit var glide: RequestManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutstargramMyPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPostRecyclerView = binding.myPostRecyclerview
        glide = Glide.with(this)
        createList()

        binding.showInfo.setOnClickListener {
            startActivity(Intent(this,OutstargramUserInfo::class.java))
        }
        binding.allList.setOnClickListener {
            startActivity(Intent(this, OutStargramPostListActivity::class.java))
        }
        binding.showUpload.setOnClickListener {
            startActivity(Intent(this,OutstargramUploadActivity::class.java))
        }

    }

    fun createList() {
        (application as MasterApplication).service.getMyPostList().enqueue(
            object: Callback<ArrayList<Post>>{
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if(response.isSuccessful){
                        val myPostList = response.body()
                        val adapter = MyPostAdapter(myPostList!!, LayoutInflater.from(this@OutstargramMyPostListActivity),glide)
                        myPostRecyclerView.adapter = adapter
                        myPostRecyclerView.layoutManager = LinearLayoutManager(this@OutstargramMyPostListActivity)
                    }
                }
            }
        )
    }
}

class MyPostAdapter(
    var postList: ArrayList<Post>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postOwner: TextView
        val postImage: ImageView
        val postContent: TextView

        init {
            postOwner = itemView.findViewById(R.id.post_owner)
            postImage = itemView.findViewById(R.id.post_img)
            postContent = itemView.findViewById(R.id.post_content)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.outstargram_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyPostAdapter.ViewHolder, position: Int) {
        holder.postOwner.setText(postList.get(position).owner)
        holder.postContent.setText(postList.get(position).content)
        glide.load(postList.get(position).image).into(holder.postImage)
    }


}
