package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.CategoriesAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.CategoriesDb.CategoriesDao
import com.example.medishop.RoomDB.CategoriesDb.CategoriesDatabase
import com.example.medishop.RoomDB.CategoriesModel

class CategoriesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var categoriesDatabase: CategoriesDatabase? = null
    private var categoriesDao: CategoriesDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        val categories: MutableList<CategoriesModel> = ArrayList()
        categories.add(
            CategoriesModel(
                "cat_1",
                "Fitness Supplements",
                "Immunity boosters, multivitamins"
            )
        )
        // Add other categories here

        recyclerView = view.findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = linearLayoutManager
        val customAdapter = CategoriesAdapter(requireContext(), categories)
        recyclerView?.adapter = customAdapter
        return view
    }
}
