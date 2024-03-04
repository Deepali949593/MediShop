package com.example.medishop.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.R
import com.example.medishop.RoomDB.Brand

class BrandAdapter(var context: Context, var categories: List<Brand>) :
    RecyclerView.Adapter<BrandAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.brand_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: " + categories[position].cImage)
        val res = context.resources
        val mDrawableName = categories[position].cImage
        val resID = res.getIdentifier(mDrawableName, "drawable", context.packageName)
        val drawable = res.getDrawable(resID)
        holder.catImage.setImageDrawable(drawable)
        holder.title.text = categories[position].title
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView
        var title: TextView

        init {
            catImage = itemView.findViewById<View>(R.id.catImage) as ImageView
            title = itemView.findViewById(R.id.title)
        }
    }
}