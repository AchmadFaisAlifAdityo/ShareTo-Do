package com.example.sharetodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
            finish()
        }
        login()
    }

    private fun login(){
        loginButton.setOnClickListener{

            if(TextUtils.isEmpty(usernameoremailInput.text.toString())){
                usernameoremailInput.setError("Please enter Email!")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(passwordLogin.text.toString())) {
                passwordLogin.setError("Please enter Email!")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(usernameoremailInput.text.toString(), passwordLogin.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                       startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(this@LoginActivity, "Login Failed, please try again!", Toast.LENGTH_LONG).show()

                    }
                }
        }

        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
    }
}