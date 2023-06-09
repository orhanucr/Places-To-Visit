package com.example.orhan_ucar_odev9.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.orhan_ucar_odev9.databinding.ActivityUplaodBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

import java.util.*

class UplaodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUplaodBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    var selectedPicture: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUplaodBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage
        registerLauncher()

        binding.backButton.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun uploadButton(view: View) {
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"
        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)

        // Kullanıcı resim seçmediyse, varsayılan bir resim atama işlemi
        if (selectedPicture == null) {
            val defaultImageUri = Uri.parse("android.resource://com.example.orhan_ucar_odev9/drawable/select")
            selectedPicture = defaultImageUri
        }

        if (selectedPicture != null) {
            imageReference.putFile(selectedPicture!!).addOnSuccessListener() {
                val uploadPictureReference = storage.reference.child("images").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()

                    if (auth.currentUser != null) {
                        val postMap = hashMapOf<String, Any>()
                        postMap.put("downloadUrl", downloadUrl)
                        postMap.put("userEmail", auth.currentUser!!.email!!)
                        postMap.put("baslik", binding.baslikText.text.toString())
                        postMap.put("sehir", binding.sehirText.text.toString())
                        postMap.put("notlar", binding.notlarText.text.toString())
                        postMap.put("date", Timestamp.now())

                        firestore.collection("Posts").add(postMap).addOnSuccessListener {
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(
                                this@UplaodActivity,
                                it.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }.addOnSuccessListener {
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            Toast.makeText(this, "Fotoğraf seçimi zorunludur!", Toast.LENGTH_LONG).show()
        }


    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(view, "Permission needed for gallery!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission") {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }

    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data

                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data
                        selectedPicture?.let {
                            binding.imageView.setImageURI(it)
                        }
                    }
                }
            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    Toast.makeText(this@UplaodActivity, "Permission needed!", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}