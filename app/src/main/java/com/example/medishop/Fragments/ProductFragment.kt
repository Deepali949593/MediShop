package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.ProductAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.ProductDb.ProductDao
import com.example.medishop.RoomDB.ProductDb.ProductDatabase
import com.example.medishop.Session.SessionManager

class ProductFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null
    private var c_id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        productDatabase = ProductDatabase.getInstance(requireContext())
        productDao = productDatabase!!.dao
        recyclerView = view.findViewById(R.id.recyclerView)
        c_id = requireArguments().getString("c_id")
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        val productList = productDao!!.allProduct.orEmpty()
        if (productList.isEmpty()) {
            val sessionManager = SessionManager(requireContext())
            sessionManager.addProducts()
        }

        val customAdapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = customAdapter

        return view
    }
}
