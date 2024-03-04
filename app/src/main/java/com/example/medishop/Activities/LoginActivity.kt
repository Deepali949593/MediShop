package com.example.medishop.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.medishop.R
import com.example.medishop.RoomDB.UserDb.UserDAO
import com.example.medishop.RoomDB.UserDb.UserDatabase
import com.example.medishop.Session.SessionManager
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var loginButton: AppCompatButton? = null
    var verifyButton: AppCompatButton? = null
    var phoneEditText: EditText? = null
    var otpEditText: EditText? = null
    var mAuth: FirebaseAuth? = null
    var verificationsId: String? = null
    var txtInfo: TextView? = null
    var phoneLayout: LinearLayout? = null
    var otpLayout: LinearLayout? = null
    var registerLayout: LinearLayout? = null
    var progressBar: ProgressBar? = null
    private var userDatabase: UserDatabase? = null
    private var userDAO: UserDAO? = null
    var phone: String? = null
    var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)
        initUI()
        userDB()
    }

    private fun userDB() {
        userDatabase = UserDatabase.getInstance(this)
        userDAO = userDatabase?.dao
        if (userDatabase == null || userDAO == null) {
            // Handle the case where userDatabase or userDAO is null
            // You might want to show an error message or take appropriate action.
            Log.e("LoginActivity", "Failed to initialize user database")
        }
    }

    private fun initUI() {
        phoneEditText = findViewById(R.id.phoneEditText)
        otpEditText = findViewById(R.id.otpEditText)
        verifyButton = findViewById(R.id.verifyButton)
        loginButton = findViewById(R.id.loginButton)
        txtInfo = findViewById(R.id.txtInfo)
        progressBar = findViewById(R.id.progressBar)
        phoneLayout = findViewById(R.id.phoneLayout)
        otpLayout = findViewById(R.id.otpLayout)
        registerLayout = findViewById(R.id.registerLayout)
        phoneLayout!!.visibility = View.VISIBLE
        otpLayout!!.visibility = View.INVISIBLE
        verifyButton!!.setOnClickListener(this)
        loginButton!!.setOnClickListener(this)
        registerLayout!!.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> if (TextUtils.isEmpty(phoneEditText!!.text.toString())) phoneEditText!!.error =
                "Enter phone number" else if (phoneEditText!!.text.toString().length == 10) {
                if (userDAO!!.userExists(phoneEditText!!.text.toString())) {
                    progressBar!!.visibility = View.VISIBLE
                    phoneLayout!!.visibility = View.INVISIBLE
                    otpLayout!!.visibility = View.INVISIBLE
                    phone = phoneEditText!!.text.toString()
                    sendVerificationCode(phoneEditText!!.text.toString())
                } else {
                    Toast.makeText(
                        applicationContext,
                        "User doesn't exists. Please register!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else phoneEditText!!.error = "Invalid phone number"

            R.id.verifyButton -> if (TextUtils.isEmpty(otpEditText!!.text.toString())) otpEditText!!.error =
                "Enter otp" else {
                VerifyCode(otpEditText!!.text.toString())
            }

            R.id.registerLayout -> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val code = credential.smsCode
                code?.let { VerifyCode(it) }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w("TAG", "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(applicationContext, "invaild", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(applicationContext, "quota", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    Toast.makeText(applicationContext, "Recaptacha", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                verificationsId = verificationId
                verifyButton!!.isEnabled = true
                progressBar!!.visibility = View.INVISIBLE
                phoneLayout!!.visibility = View.INVISIBLE
                otpLayout!!.visibility = View.VISIBLE
            }
        }

    private fun VerifyCode(code: String) {
        val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationsId!!, code)
        signinByCredentials(phoneAuthCredential)
    }

    private fun signinByCredentials(phoneAuthCredential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = userDAO!!.getUserDetails(phone)
                    sessionManager!!.createLoginSession(user.email, user.name, user.phone)
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }

    private fun sendVerificationCode(number: String) {
        phoneLayout!!.visibility = View.INVISIBLE
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber("+91$number") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // (optional) Activity for callback binding
            // If no activity is passed, reCAPTCHA verification can not be used.
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        txtInfo!!.text = "Enter the otp sent to +91$number"
    }

    override fun onStart() {
        super.onStart()
    }
}
