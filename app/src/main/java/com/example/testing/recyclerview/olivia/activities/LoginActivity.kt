package com.example.testing.recyclerview.olivia.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testing.recyclerview.olivia.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import models.User


class LoginActivity : AppCompatActivity() {

    lateinit var googleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 11
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        if (auth.currentUser!=null){
            gotoNextActivity()
        }

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun gotoNextActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account: GoogleSignInAccount = task.result
            authWithGoogle(account.idToken)
        }
    }

    fun authWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val firebaseUser =
                        User(user?.uid, user?.displayName, user?.photoUrl.toString(), "Unknown", 500)
                    if (user != null) {
                        database.reference
                            .child("profiles")
                            .child(user.uid)
                            .setValue(firebaseUser)
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    finishAffinity()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.localizedMessage,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                    Log.e("profile", user?.getPhotoUrl().toString());
                } else {
                    Log.e("err", task.exception!!.localizedMessage)
                }
            })
    }

    fun Login_Clicked(view: View) {

        val Intent = googleSignInClient.signInIntent

        startActivityForResult(Intent, RC_SIGN_IN)

    }

}




