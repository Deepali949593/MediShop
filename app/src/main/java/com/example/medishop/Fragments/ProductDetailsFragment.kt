package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.ProductSimilarAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.CartDb.Cart
import com.example.medishop.RoomDB.CartDb.CartDao
import com.example.medishop.RoomDB.CartDb.CartDatabase
import com.example.medishop.RoomDB.ProductDb.ProductDao
import com.example.medishop.RoomDB.ProductDb.ProductDatabase
import com.example.medishop.Session.SessionManager

class ProductDetailsFragment : Fragment() {
    var view: View? = null
    var recyclerView: RecyclerView? = null
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null
    private var cartDao: CartDao? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_product_details, container, false)
        initCategory()
        val catImage = view.findViewById<ImageView>(R.id.catImage)
        val product_name = view.findViewById<TextView>(R.id.product_name)
        val product_details = view.findViewById<TextView>(R.id.product_details)
        val product_price = view.findViewById<TextView>(R.id.product_price)
        val product_rating = view.findViewById<TextView>(R.id.product_rating)
        val cartButton = view.findViewById<AppCompatButton>(R.id.cartButton)
        cartDao = CartDatabase.getInstance(context).dao
        val res = context!!.resources
        val mDrawableName = requireArguments()["image"].toString()
        val resID = res.getIdentifier(mDrawableName, "drawable", context!!.packageName)
        val drawable = res.getDrawable(resID)
        catImage.setImageDrawable(drawable)
        product_name.text = requireArguments()["title"].toString()
        product_details.text = requireArguments()["details"].toString()
        product_price.text = "₹ " + requireArguments()["price"].toString()
        product_rating.text = requireArguments()["rating"].toString()
        cartButton.setOnClickListener {
            if (!cartDao.CartExists(requireArguments()["id"].toString())) {
                cartDao.insertCart(
                    Cart(
                        requireArguments()["id"].toString(),
                        requireArguments()["title"].toString(),
                        requireArguments()["image"].toString(),
                        requireArguments()["price"].toString(),
                        requireArguments()["details"].toString(),
                        requireArguments()["rating"].toString(),
                        "1"
                    )
                )
                Toast.makeText(context, "Successfully added to cart!!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun initCategory() {
        recyclerView = view!!.findViewById(R.id.catRecyclerView)
        val glm = LinearLayoutManager(context)
        glm.orientation = LinearLayoutManager.HORIZONTAL // set Horizontal Orientation
        recyclerView.setLayoutManager(glm)
        productDatabase = ProductDatabase.getInstance(context)
        productDao = productDatabase.dao
        if (productDao.allProduct.size == 0) {
            val sessionManager = SessionManager(context)
            sessionManager.addProducts()
        }
        val customAdapter = ProductSimilarAdapter(context!!, productDao.allProduct)
        recyclerView.setAdapter(customAdapter)
    }
}