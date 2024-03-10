package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.ProductAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.ProductDb.Product
import com.example.medishop.RoomDB.ProductDb.ProductDao
import com.example.medishop.RoomDB.ProductDb.ProductDatabase
import com.example.medishop.Session.SessionManager
import java.util.*

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBarText: SearchView
    private var productDao: ProductDao? = null
    private var myProducts: List<Product> = ArrayList()
    private var customAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initCategory(view)
        searchBarText = view.findViewById(R.id.searchBar)
        searchBarText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return true
            }
        })
        return view
    }

    private fun filter(newText: String) {
        val filteredList: MutableList<Product> = ArrayList()
        for (product in myProducts) {
            if (product.pName.lowercase(Locale.getDefault())
                    .contains(newText.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(product)
            }
        }
        customAdapter?.filteredList(filteredList)
    }

    private fun initCategory(view: View) {
        recyclerView = view.findViewById(R.id.catRecyclerView)
        val glm = LinearLayoutManager(context)
        glm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = glm
        val productDatabase = ProductDatabase.getInstance(requireContext())
        productDao = productDatabase.dao
        if (productDao?.allProduct?.size == 0) {
            val sessionManager = SessionManager(requireContext())
            sessionManager.addProducts()
        }
        myProducts = productDao?.allProduct ?: emptyList()
        customAdapter = ProductAdapter(requireContext(), myProducts)
        recyclerView.adapter = customAdapter
    }
}
