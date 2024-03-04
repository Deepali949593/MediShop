package com.example.medishop.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Fragments.CartFragment
import com.example.medishop.R
import com.example.medishop.RoomDB.CartDb.Cart
import com.example.medishop.RoomDB.CartDb.CartDao
import com.example.medishop.RoomDB.CartDb.CartDatabase

class CartAdapter(var context: Context, var product: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    private var cartDatabase: CartDatabase? = null
    private var cartDao: CartDao? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_layout, parent, false)
        val vh = MyViewHolder(v) // pass the view to View Holder
        cartDatabase = CartDatabase.getInstance(context)
        cartDao = cartDatabase?.dao
        return vh
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        Log.d("TAG", "onBindViewHolder: " + product[position].cId)
        val res = context.resources
        val mDrawableName = product[position].cImage
        val resID = res.getIdentifier(mDrawableName, "drawable", context.packageName)
        val drawable = res.getDrawable(resID)
        holder.catImage.setImageDrawable(drawable)
        holder.product_name.text = product[position].cName
        holder.product_price.text = "₹ " + product[position].cPrice
        holder.product_qty.text = product[position].cQty
        holder.minusImage.setOnClickListener { v ->
            val qty = holder.product_qty.text.toString().toInt()
            if (qty > 1) {
                holder.product_qty.text = String.format("%d", qty - 1)
                cartDao!!.updateCart(
                    Cart(
                        product[position].cId,
                        product[position].cName,
                        product[position].cImage,
                        product[position].cPrice,
                        product[position].cDetails,
                        product[position].cRate,
                        String.format("%d", qty - 1)
                    )
                )
                val activity = v.context as AppCompatActivity
                val myFragment: Fragment = CartFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_home, myFragment).addToBackStack(null)
                    .commit()
            }
        }
        holder.plusImage.setOnClickListener { v ->
            val qty = holder.product_qty.text.toString().toInt()
            if (qty >= 0) holder.product_qty.text = String.format("%d", qty + 1)
            cartDao!!.updateCart(
                Cart(
                    product[position].cId,
                    product[position].cName,
                    product[position].cImage,
                    product[position].cPrice,
                    product[position].cDetails,
                    product[position].cRate,
                    String.format("%d", qty + 1)
                )
            )
            val activity = v.context as AppCompatActivity
            val myFragment: Fragment = CartFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_home, myFragment).addToBackStack(null)
                .commit()
        }
        holder.deleteImage.setOnClickListener { v ->
            cartDao!!.delete(product[position].cId)
            notifyItemRemoved(position)
            val activity = v.context as AppCompatActivity
            val myFragment: Fragment = CartFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_home, myFragment).addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView
        var minusImage: ImageView
        var plusImage: ImageView
        var deleteImage: ImageView
        var product_name: TextView
        var product_price: TextView
        var product_qty: TextView

        init {
            catImage = itemView.findViewById<View>(R.id.catImage) as ImageView
            product_name = itemView.findViewById<View>(R.id.product_name) as TextView
            product_qty = itemView.findViewById<View>(R.id.product_qty) as TextView
            minusImage = itemView.findViewById<View>(R.id.minusImage) as ImageView
            plusImage = itemView.findViewById<View>(R.id.plusImage) as ImageView
            deleteImage = itemView.findViewById<View>(R.id.deleteImage) as ImageView
            product_price = itemView.findViewById<View>(R.id.product_price) as TextView
        }
    }
}
