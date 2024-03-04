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
    var view: View? = null
    var recyclerView: RecyclerView? = null
    private val categoriesDatabase: CategoriesDatabase? = null
    private val categoriesDao: CategoriesDao? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false)
        val categories: MutableList<CategoriesModel> = ArrayList()
        categories.add(
            CategoriesModel(
                "cat_1",
                "Fitness Supplements",
                "Immunity boosters, multivitamins"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_2",
                "Ayurvedic Care",
                "Ayurvedic medicine, ayurvedic foods & juices"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_3",
                "Diabetic Care",
                "Diabetic & Ortho footwear, supplements"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_4",
                "Health Conditions",
                "Stomach care, headache, kidney care"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_5",
                "Sexual Wellness",
                "Condoms, Performance booster, lubricants"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_6",
                "Skin Care",
                "Skin creams & Hair removal creams, face wipes"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_7",
                "Health Food and Drinks",
                "Nutritional drinks, health foods"
            )
        )
        categories.add(
            CategoriesModel(
                "cat_8",
                "Elderly Care",
                "Urinary support and care, adult diapers"
            )
        )
        categories.add(CategoriesModel("cat_9", "Personal Care", "Men care, oral care, body care"))
        categories.add(
            CategoriesModel(
                "cat_10",
                "Mother & Baby Care",
                "Baby hygiene, baby food, baby skin care"
            )
        )
        recyclerView = view.findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL // set Horizontal Orientation
        recyclerView.setLayoutManager(linearLayoutManager)
        val customAdapter = CategoriesAdapter(context!!, categories)
        recyclerView.setAdapter(customAdapter)
        return view
    }
}