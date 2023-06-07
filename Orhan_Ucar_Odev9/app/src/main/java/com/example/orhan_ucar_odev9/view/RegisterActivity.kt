package com.example.orhan_ucar_odev9.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.orhan_ucar_odev9.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        auth = Firebase.auth
    }

    fun registerClicked(view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(this@RegisterActivity, "Successful", Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@RegisterActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }


    fun backClicked(view: View) {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}