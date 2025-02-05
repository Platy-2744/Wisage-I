package com.example.wisage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    private var ptxtEmail: EditText? = null
    private var ptxtPassword: EditText? = null
    private var btnSignUp: Button? = null
    private var checkTxt: CheckedTextView? = null
    /*val authState = AuthViewModel.authState.observeAsState()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        when(AuthViewModel.authState.value){
            is AuthState.Authenticated -> startActivity(Intent(this,MainActivity::class.java))
            is AuthState.Error -> Toast.makeText(this,
                (AuthViewModel.authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()
            else -> Unit
        }

        btnSignUp!!.setOnClickListener{
            val mail = ptxtEmail!!.text.toString().trim()
            val pass = ptxtPassword!!.text.toString().trim()
            if (mail.isEmpty()||pass.isEmpty()){
                ptxtPassword!!.setError("Somethig went wrong")
            }else{
                AuthViewModel.signup(mail,pass)
                startActivity(Intent(this,Login::class.java))
            }

        }
        checkTxt!!.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }
    }
    private fun init(){
        ptxtEmail = findViewById(R.id.sig_ptxt_email)
        ptxtPassword = findViewById(R.id.sig_ptxt_pass)
        btnSignUp = findViewById(R.id.sig_btn_signup)
        checkTxt = findViewById(R.id.sig_checktxt_tologin)
    }

}

