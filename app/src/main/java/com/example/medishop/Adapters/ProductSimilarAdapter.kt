package com.example.medishop.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Fragments.ProductDetailsFragment
import com.example.medishop.R
import com.example.medishop.RoomDB.CartDb.Cart
import com.example.medishop.RoomDB.CartDb.CartDao
import com.example.medishop.RoomDB.CartDb.CartDatabase
import com.example.medishop.RoomDB.ProductDb.Product

class ProductSimilarAdapter(var context: Context, var product: List<Product?>?) :
    RecyclerView.Adapter<ProductSimilarAdapter.MyViewHolder>() {
    private var cartDatabase: CartDatabase? = null
    private var cartDao: CartDao? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_similar_layout, parent, false)
        val vh = MyViewHolder(v) // pass the view to View Holder
        cartDatabase = CartDatabase.getInstance(context)
        cartDao = cartDatabase?.dao
        return vh
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val currentProduct = product?.get(position)
        currentProduct?.let { product ->
            // Access properties safely
            val res = context.resources
            val mDrawableName = product.pImage
            val resID = res.getIdentifier(mDrawableName, "drawable", context.packageName)
            val drawable = res.getDrawable(resID)
            holder.catImage.setImageDrawable(drawable)
            holder.product_name.text = product.pName
            holder.product_details.text = product.pDetails
            holder.product_price.text = "₹ " + product.pPrice
            holder.product_rating.text = product.pRate

            // Click listener for item
            holder.itemView.setOnClickListener {
                val activity = context as AppCompatActivity
                val args = Bundle()
                args.putString("image", product.pImage)
                args.putString("title", product.pName)
                args.putString("details", product.pDetails)
                args.putString("rating", product.pRate)
                args.putString("price", product.pPrice)
                args.putString("id", product.pId)
                val fm: Fragment = ProductDetailsFragment()
                fm.arguments = args
                activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.nav_host_fragment_activity_home, fm).commit()
            }

            // Click listener for cart button
            holder.cartButton.setOnClickListener {
                cartDao?.let { dao ->
                    if (!dao.CartExists(product.pId)) {
                        dao.insertCart(
                            Cart(
                                product.pId,
                                product.pName,
                                product.pImage,
                                product.pPrice,
                                product.pDetails,
                                product.pRate,
                                "1"
                            )
                        )
                        Toast.makeText(context, "Successfully added to cart!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return product?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView
        var product_name: TextView
        var product_details: TextView
        var product_rating: TextView
        var product_price: TextView
        var cartButton: AppCompatButton

        init {
            catImage = itemView.findViewById<View>(R.id.catImage) as ImageView
            product_name = itemView.findViewById<View>(R.id.product_name) as TextView
            product_details = itemView.findViewById<View>(R.id.product_details) as TextView
            product_rating = itemView.findViewById<View>(R.id.product_rating) as TextView
            product_price = itemView.findViewById<View>(R.id.product_price) as TextView
            cartButton = itemView.findViewById<View>(R.id.cartButton) as AppCompatButton
        }
    }
}
