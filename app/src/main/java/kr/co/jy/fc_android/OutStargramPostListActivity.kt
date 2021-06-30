package kr.co.jy.fc_android

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
import kr.co.jy.fc_android.databinding.ActivityOutStargramPostListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.zip.Inflater

class OutStargramPostListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStargramPostListBinding
    lateinit var glide: RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStargramPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        glide = Glide.with(this)

        (application as MasterApplication).service.getAllPosts().enqueue(
            object: Callback<ArrayList<Post>>{
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if(response.isSuccessful){
                        val postList = response.body()
                        val adapter = PostAdapter(postList!!, LayoutInflater.from(this@OutStargramPostListActivity),glide)
                        binding.postRecyclerview.adapter = adapter
                        binding.postRecyclerview.layoutManager= LinearLayoutManager(this@OutStargramPostListActivity)

                    }

                }
            }
        )
    }


}

class PostAdapter(
    var postList: ArrayList<Post>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postOwner.setText(postList.get(position).owner)
        holder.postContent.setText(postList.get(position).content)
        glide.load(postList.get(position).image).into(holder.postImage)
    }
}
