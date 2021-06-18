package kr.co.jy.fc_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.jy.fc_android.databinding.ActivityOutStargramPostListBinding
import java.util.zip.Inflater

class OutStargramPostListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOutStargramPostListBinding
   // 추가하기! val glide: Glide? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStargramPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}

class PostAdapter(
    var postList:ArrayList<Post>,
    val inflater: LayoutInflater
):RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val postOwner: TextView
        val postImage : ImageView
        val postContent: TextView

        init{
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
    }
}
