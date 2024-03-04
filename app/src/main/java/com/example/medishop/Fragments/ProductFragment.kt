package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.ProductAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.ProductDb.ProductDao
import com.example.medishop.RoomDB.ProductDb.ProductDatabase
import com.example.medishop.Session.SessionManager

class ProductFragment : Fragment() {
    var view: View? = null
    var recyclerView: RecyclerView? = null
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null
    var c_id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false)
        productDatabase = ProductDatabase.getInstance(context)
        productDao = productDatabase.dao
        recyclerView = view.findViewById(R.id.recyclerView)
        c_id = arguments!!.getString("c_id")
        val linearLayoutManager = GridLayoutManager(context, 2)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL // set Horizontal Orientation
        recyclerView.setLayoutManager(linearLayoutManager)
        if (productDao.allProduct.size == 0) {
            val sessionManager = SessionManager(context)
            sessionManager.addProducts()
        }
        val customAdapter = ProductAdapter(context!!, productDao.allProduct)
        recyclerView.setAdapter(customAdapter)
        return view
    }
}