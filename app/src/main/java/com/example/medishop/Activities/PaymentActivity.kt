package com.example.medishop.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medishop.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    var price: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_payment)
        Checkout.preload(applicationContext)
        val bundle1 = intent.extras
        price = (bundle1!!.getString("price")!!.toInt() * 100).toString()
        Log.d("TAG", "onCreate: $price")
        makepayment()
    }

    private fun makepayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_DS6MavM1QjIs9F")
        checkout.setImage(R.mipmap.ic_launcher)
        val activity: Activity = this
        try {
            val options = JSONObject()
            options.put("name", "HealSphere")
            options.put("description", "Amount to be paid")
            options.put("image", R.mipmap.ic_launcher)
            options.put("theme.color", "#e85e2c")
            options.put("currency", "INR")
            options.put("amount", price) //300 X 100
            options.put("prefill.email", "vishal29201@example.com")
            options.put("prefill.contact", "7847819899")
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(s: String) {
        Toast.makeText(applicationContext, "Booking confirmed", Toast.LENGTH_SHORT).show()
        val i = Intent(this@PaymentActivity, HomeActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onPaymentError(i: Int, s: String) {
        Toast.makeText(applicationContext, "Payment failed", Toast.LENGTH_SHORT).show()
        val i1 = Intent(this@PaymentActivity, HomeActivity::class.java)
        startActivity(i1)
        finish()
    }
}