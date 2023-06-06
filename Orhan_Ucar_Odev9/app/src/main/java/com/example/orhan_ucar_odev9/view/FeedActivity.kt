package com.example.orhan_ucar_odev9.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orhan_ucar_odev9.adapter.FeedRecyclerViewAdapter
import com.example.orhan_ucar_odev9.databinding.ActivityFeedBinding
import com.example.orhan_ucar_odev9.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var feedAdapter: FeedRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerViewAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter

        binding.imageViewLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@FeedActivity, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, UplaodActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getData() {
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents
                        postArrayList.clear()
                        for (documents in documents) {

                            val userEmail = documents.get("userEmail") as String
                            val baslik = documents.get("baslik") as String
                            val sehir = documents.get("sehir") as String
                            val notlar = documents.get("notlar") as String
                            val downloadUrl = documents.get("downloadUrl") as String

                            val post = Post(userEmail, baslik, sehir, notlar,downloadUrl)
                            postArrayList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

}