package com.example.orhan_ucar_odev9.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.orhan_ucar_odev9.databinding.ActivityResetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.btnReset.setOnClickListener {
            val emailAddress = binding.remailText.text.toString()

            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Şifre sıfırlama e-postası gönderildi
                        Toast.makeText(this, "Şifre sıfırlama e-postası gönderildi.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Şifre sıfırlama e-postası gönderilemedi
                        Toast.makeText(this, "Şifre sıfırlama e-postası gönderilemedi.", Toast.LENGTH_SHORT).show()
                    }
                }

            val intent = Intent(this@ResetActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}
