package com.example.orhan_ucar_odev9.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orhan_ucar_odev9.databinding.RecyclerRowBinding
import com.example.orhan_ucar_odev9.model.Post
import com.squareup.picasso.Picasso

class FeedRecyclerViewAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<FeedRecyclerViewAdapter.PostHolder>() {

    private var onItemLongClickListener: OnItemLongClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class PostHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.recyclerBaslikText.text = post.baslik
            binding.recyclerSehirText.text = post.sehir
            binding.recyclerNotlarText.text = post.notlar
            Picasso.get().load(post.downloadUrl).into(binding.recyclerImageView)

            binding.root.setOnLongClickListener {
                onItemLongClickListener?.onItemLongClick(it, adapterPosition)
                true
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: android.view.View, position: Int)
    }
}
