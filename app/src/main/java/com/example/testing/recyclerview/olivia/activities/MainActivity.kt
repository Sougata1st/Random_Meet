package com.example.testing.recyclerview.olivia.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.testing.recyclerview.olivia.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.annotations.NotNull
import models.User


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var permissions: Array<String>
    lateinit var user: User
    val REQ_CODE = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        val database = FirebaseDatabase.getInstance()
        permissions = arrayOf(
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.MODIFY_AUDIO_SETTINGS"
        )


        database.reference.child("profiles")
            .child(currentuser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                        user = snapshot.getValue(User::class.java)!!

                        Glide.with(this@MainActivity)
                            .load(user.getProfile())
                            .into(binding.userProfile)

                }

                override fun onCancelled(@NotNull error: DatabaseError) {}
            })

        database.reference.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.textView6.text= "activeusers : ${snapshot.childrenCount.toString()}"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
            )

    }

    fun findclicked(view: View) {
        try {
            requestPermissions(permissions, REQ_CODE)
        }catch ( e :Exception){
            Toast.makeText(this,"caught exception",Toast.LENGTH_LONG)
                .show()
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                try {
                    val intent = Intent(this@MainActivity, Connectingactivity::class.java)
                    intent.putExtra("userProfile", user.getProfile())
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Please turn on internet connection", Toast.LENGTH_LONG)
                        .show()
                }


            } else {
                Toast.makeText(this, "Please allow the permissions to continue", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}

