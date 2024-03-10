package com.example.medishop.Fragments

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
    private lateinit var recyclerView: RecyclerView
    private lateinit var paymentButton: AppCompatButton
    private var price = 0
    private var product: List<Cart>? = null
    private lateinit var totalPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val cartDatabase = CartDatabase.getInstance(requireContext())!!
        val cartDao = cartDatabase.dao!!
        recyclerView = view.findViewById(R.id.recyclerView)
        paymentButton = view.findViewById(R.id.paymentButton)
        totalPrice = view.findViewById(R.id.totalPrice)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = linearLayoutManager
        paymentButton.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("price", price.toString())
            startActivity(intent)
            requireActivity().finish()
        }
        product = cartDao.allCart as List<Cart>?
        if (product != null) {
            for (i in product!!.indices) {
                price += product!![i].cPrice.toInt() * product!![i].cQty.toInt()
            }
            val customAdapter = CartAdapter(requireContext(), product!!)
            recyclerView.adapter = customAdapter
            totalPrice.text = "₹ $price"
        }
        return view
    }
}
