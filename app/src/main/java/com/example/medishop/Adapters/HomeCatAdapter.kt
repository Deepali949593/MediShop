package com.example.medishop.Adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Fragments.ProductFragment
import com.example.medishop.R
import com.example.medishop.RoomDB.CategoriesModel

class HomeCatAdapter(var context: Context, var categories: List<CategoriesModel>) :
    RecyclerView.Adapter<HomeCatAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.home_cat_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: " + categories[position].getcImage())
        val res = context.resources
        val mDrawableName = categories[position].getcImage()
        val resID = res.getIdentifier(mDrawableName, "drawable", context.packageName)
        val drawable = res.getDrawable(resID)
        holder.catImage.setImageDrawable(drawable)
        holder.title.text = categories[position].title
        holder.itemView.setOnClickListener {
            val activity = context as AppCompatActivity
            val args = Bundle()
            val fm: Fragment = ProductFragment()
            fm.arguments = args
            activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.nav_host_fragment_activity_home, fm).commit()
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView
        var title: TextView

        init {
            catImage = itemView.findViewById<View>(R.id.catImage) as ImageView
            title = itemView.findViewById<View>(R.id.title) as TextView
        }
    }
}