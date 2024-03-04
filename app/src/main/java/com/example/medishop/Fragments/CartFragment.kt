package com.example.medishop.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Activities.PaymentActivity
import com.example.medishop.Adapters.CartAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.CartDb.Cart
import com.example.medishop.RoomDB.CartDb.CartDao
import com.example.medishop.RoomDB.CartDb.CartDatabase

class CartFragment : Fragment() {
    var view: View? = null
    var recyclerView: RecyclerView? = null
    var paymentButton: AppCompatButton? = null
    var c_id: String? = null
    var price = 0
    var product: List<Cart>? = null
    var totalPrice: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false)
        cartDatabase = CartDatabase.getInstance(context)
        cartDao = cartDatabase.dao
        recyclerView = view.findViewById(R.id.recyclerView)
        paymentButton = view.findViewById(R.id.paymentButton)
        totalPrice = view.findViewById(R.id.totalPrice)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL // set Horizontal Orientation
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setLayoutManager(linearLayoutManager)
        paymentButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("price", price.toString() + "")
            startActivity(intent)
            (context as Activity?)!!.finish()
        })
        if (cartDao.allCart != null) {
            product = cartDao.allCart
            for (i in product.indices) {
                price += product.get(i).cPrice.toInt() * product.get(i).cQty.toInt()
            }
            val customAdapter = CartAdapter(context!!, product)
            recyclerView.setAdapter(customAdapter)
            totalPrice.setText("₹ $price")
        }
        return view
    }

    companion object {
        private var cartDatabase: CartDatabase? = null
        private var cartDao: CartDao? = null
    }
}