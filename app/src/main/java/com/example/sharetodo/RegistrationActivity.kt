package com.example.sharetodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.lang.ref.Reference

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register(){
        RegisterButton.setOnClickListener{

            if(TextUtils.isEmpty(UsernameInput.text.toString())){
                username.setError("Please enter Username!")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(emailInput.text.toString())) {
                emailInput.setError("Please enter Email!")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(passwordInput.text.toString())) {
                passwordInput.setError("Please enter Password!")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val currentUser = auth.currentUser
                            val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                            currentUserDb?.child("Username")?.setValue(UsernameInput.text.toString())

                            Toast.makeText(this@RegistrationActivity, "Registration Success!", Toast.LENGTH_LONG).show()
                            finish()

                        }else{
                            Toast.makeText(this@RegistrationActivity, "Registration Failed, please try again!", Toast.LENGTH_LONG).show()

                        }
                    }

        }
    }
}