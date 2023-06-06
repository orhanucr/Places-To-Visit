package com.example.orhan_ucar_odev9.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orhan_ucar_odev9.databinding.RecyclerRowBinding
import com.example.orhan_ucar_odev9.model.Post
import com.squareup.picasso.Picasso


class FeedRecyclerViewAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<FeedRecyclerViewAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        //holder.binding.recyclerViewEmailText.text = postList.get(position).email
        holder.binding.recyclerBaslikText.text = postList.get(position).baslik
        holder.binding.recyclerSehirText.text = postList.get(position).sehir
        holder.binding.recyclerNotlarText.text = postList.get(position).notlar
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}