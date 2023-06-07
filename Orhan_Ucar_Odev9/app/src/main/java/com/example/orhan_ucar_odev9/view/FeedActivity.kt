package com.example.orhan_ucar_odev9.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, UplaodActivity::class.java)
            startActivity(intent)
        }

        feedAdapter.setOnItemLongClickListener(object :
            FeedRecyclerViewAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                val post = postArrayList[position]

                val alertDialogBuilder = AlertDialog.Builder(this@FeedActivity)
                alertDialogBuilder.setMessage("Veriyi silmek istiyor musunuz?")
                alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->
                    deletePost(post)
                    dialog.dismiss()
                }
                alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->
                    dialog.dismiss()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        })


    }

    //Giriş Yapmış Olan Kullanıcının Kayıtlı Verilerini Alma
    private fun getData() {
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email

        db.collection("Posts")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                postArrayList.clear()
                for (document in querySnapshot.documents) {
                    val id = document.id
                    val postUserEmail = document.getString("userEmail")
                    if (postUserEmail == userEmail) {
                        val baslik = document.getString("baslik") ?: ""
                        val sehir = document.getString("sehir") ?: ""
                        val notlar = document.getString("notlar") ?: ""
                        val downloadUrl = document.getString("downloadUrl") ?: ""

                        val post = Post(id, postUserEmail!!, baslik, sehir, notlar, downloadUrl)
                        postArrayList.add(post)
                    }
                }

                feedAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Veriler alınırken bir hata oluştu.", Toast.LENGTH_SHORT)
                    .show()
                Log.e(TAG, "Error getting documents: ", exception)
            }
    }

    /* Tüm Kullanıcıların Oluşturduğu Post'ları Alma
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

                            val id = documents.id
                            val userEmail = documents.get("userEmail") as String
                            val baslik = documents.get("baslik") as String
                            val sehir = documents.get("sehir") as String
                            val notlar = documents.get("notlar") as String
                            val downloadUrl = documents.get("downloadUrl") as String

                            val post = Post(id,userEmail, baslik, sehir, notlar,downloadUrl)
                            postArrayList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()

                    }
                }
            }
        }*/

    private fun deletePost(post: Post) {
        db.collection("Posts").document(post.id).delete()
            .addOnSuccessListener {
                feedAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Veri silindi.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Veriyi silerken bir hata oluştu: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}