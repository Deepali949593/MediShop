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
import java.util.Locale

class SearchFragment : Fragment() {
    var view: View? = null
    var recyclerView: RecyclerView? = null
    var searchBarText: SearchView? = null
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null
    var myProducts: List<Product>? = null
    var customAdapter: ProductAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_search, container, false)
        initCategory()
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
        for (product in myProducts!!) {
            if (product.pName.lowercase(Locale.getDefault())
                    .contains(newText.lowercase(Locale.getDefault()))
            ) {
                filteredList.add(product)
            }
        }
        customAdapter!!.filteredList(filteredList)
    }

    private fun initCategory() {
        recyclerView = view!!.findViewById(R.id.catRecyclerView)
        val glm = LinearLayoutManager(context)
        glm.orientation = LinearLayoutManager.VERTICAL // set Horizontal Orientation
        recyclerView.setLayoutManager(glm)
        productDatabase = ProductDatabase.getInstance(context)
        productDao = productDatabase.dao
        if (productDao.allProduct.size == 0) {
            val sessionManager = SessionManager(context)
            sessionManager.addProducts()
        }
        myProducts = productDao.allProduct
        customAdapter = ProductAdapter(context!!, productDao.allProduct)
        recyclerView.setAdapter(customAdapter)
    }
}