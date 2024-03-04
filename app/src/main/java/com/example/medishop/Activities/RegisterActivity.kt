package com.example.medishop.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.medishop.R
import com.example.medishop.RoomDB.UserDb.User
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
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var nameEdit: EditText? = null
    private var phoneEdit: EditText? = null
    private var emailEdit: EditText? = null
    private var otpEditText: EditText? = null
    private var verifyButton: AppCompatButton? = null
    private var registerButton: AppCompatButton? = null
    private var txtInfo: TextView? = null
    private var testModeText: TextView? = null
    private var progressBar: ProgressBar? = null
    private var otpLayout: LinearLayout? = null
    private var registerLayout: LinearLayout? = null
    private var loginLayout: LinearLayout? = null
    private var mAuth: FirebaseAuth? = null
    private var verificationsId: String? = null
    private var userDatabase: UserDatabase? = null
    private var userDAO: UserDAO? = null
    private var sessionManager: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_register)
        initUI()
    }

    private fun initUI() {
        nameEdit = findViewById(R.id.nameEditText)
        phoneEdit = findViewById(R.id.phoneEditText)
        emailEdit = findViewById(R.id.emailEditText)
        otpEditText = findViewById(R.id.otpEditText)
        verifyButton = findViewById(R.id.verifyButton)
        registerButton = findViewById(R.id.RegisterButton)
        txtInfo = findViewById(R.id.txtInfo)
        progressBar = findViewById(R.id.progressBar)
        otpLayout = findViewById(R.id.otpLayout)
        registerLayout = findViewById(R.id.registerLayout)
        loginLayout = findViewById(R.id.loginLayout)
        testModeText = findViewById(R.id.testModeText)

        registerLayout?.visibility = View.VISIBLE
        otpLayout?.visibility = View.INVISIBLE

        verifyButton?.setOnClickListener(this)
        registerButton?.setOnClickListener(this)
        loginLayout?.setOnClickListener(this)
        testModeText?.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()
        userDatabase = UserDatabase.getInstance(this)
        userDAO = userDatabase?.dao
        sessionManager = SessionManager(applicationContext)
    }

    private fun userDB(email: String, name: String, phone: String) {
        userDAO?.insertUser(User(email, name, phone))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.RegisterButton -> {
                if (TextUtils.isEmpty(emailEdit?.text.toString())) {
                    emailEdit?.error = "Enter email"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdit?.text.toString()).matches()) {
                    emailEdit?.error = "Invalid email"
                } else if (TextUtils.isEmpty(nameEdit?.text.toString())) {
                    nameEdit?.error = "Enter name"
                } else if (TextUtils.isEmpty(phoneEdit?.text.toString())) {
                    phoneEdit?.error = "Enter phone number"
                } else if (phoneEdit?.text.toString().length == 10) {
                    if (!userDAO?.userExists(phoneEdit?.text.toString())!!) {

                    }
                }
            }
        }
    }
}
