package com.example.medishop.Activities

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.medishop.R
import com.example.medishop.Session.SessionManager

class SplashActivity : AppCompatActivity() {
    var topAnim: Animation? = null
    var bottomAnim: Animation? = null
    var logoImage: ImageView? = null
    var sloganText: TextView? = null
    var ShopText: TextView? = null
    var sessionManager: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)
        logoImage = findViewById(R.id.logoImage)
        sessionManager = SessionManager(applicationContext)
        Handler().postDelayed({
            sessionManager!!.checkLogin()
            finish()
        }, splash_screen.toLong())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

    companion object {
        var splash_screen = 4000
    }
}