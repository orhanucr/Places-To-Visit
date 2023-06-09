package com.example.orhan_ucar_odev9.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.orhan_ucar_odev9.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        //Eğer kullanıcı daha önce giriş yapmışsa direkt olarak FeedActivity sayfası açılsın
        if (currentUser != null) {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.resetPassword.setOnClickListener {
            val intent = Intent(this@MainActivity, ResetActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signInClicked(view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    fun sıgnUpClicked(view: View) {

        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()

    }
}