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
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartDao: CartDao
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        initCategory(view)
        val catImage = view.findViewById<ImageView>(R.id.catImage)
        val product_name = view.findViewById<TextView>(R.id.product_name)
        val product_details = view.findViewById<TextView>(R.id.product_details)
        val product_price = view.findViewById<TextView>(R.id.product_price)
        val product_rating = view.findViewById<TextView>(R.id.product_rating)
        val cartButton = view.findViewById<AppCompatButton>(R.id.cartButton)
        this.cartDao = CartDatabase.getInstance(requireContext())?.dao!!
        val res = requireContext().resources
        val mDrawableName = requireArguments()["image"].toString()
        val resID = res.getIdentifier(mDrawableName, "drawable", requireContext().packageName)
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
                Toast.makeText(requireContext(), "Successfully added to cart!!", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun initCategory(view: View) {
        recyclerView = view.findViewById(R.id.catRecyclerView)
        val glm = LinearLayoutManager(requireContext())
        glm.orientation = LinearLayoutManager.HORIZONTAL // set Horizontal Orientation
        recyclerView.layoutManager = glm
        productDatabase = ProductDatabase.getInstance(requireContext())
        productDao = productDatabase?.dao
        if (productDao?.allProduct?.size == 0) {
            val sessionManager = SessionManager(requireContext())
            sessionManager.addProducts()
        }
        val customAdapter = ProductSimilarAdapter(requireContext(), productDao?.allProduct)
        recyclerView.adapter = customAdapter
    }
}
