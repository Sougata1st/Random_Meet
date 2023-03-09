package com.example.testing.recyclerview.olivia.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testing.recyclerview.olivia.R
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser!=null){
            gotoNextActivity()
        }
    }

    fun get_started_clicked(view: View) {
        gotoNextActivity()
    }
    fun gotoNextActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}