package com.example.medishop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medishop.Adapters.BrandAdapter
import com.example.medishop.Adapters.HomeCatAdapter
import com.example.medishop.Adapters.SliderAdapter
import com.example.medishop.R
import com.example.medishop.RoomDB.Brand
import com.example.medishop.RoomDB.CategoriesModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment() {
    private lateinit var searchBarText: EditText
    private lateinit var recyclerViewBrand: RecyclerView
    private lateinit var recyclerViewCat: RecyclerView
    private lateinit var sliderView: SliderView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        searchBarText = view.findViewById(R.id.searchBar)
        searchBarText.setOnClickListener {
            val activity = requireContext() as AppCompatActivity
            val args = Bundle()
            val fm = ProductAdapter()
            fm.arguments = args
            activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.nav_host_fragment_activity_home, fm).commit()
        }
        initBrandRecycler(view)
        initSlider(view)
        initCategory(view)
        return view
    }

    private fun initSlider(view: View) {
        sliderView = view.findViewById(R.id.imageSlider)
        val images = intArrayOf(
            R.drawable.sl_1,
            R.drawable.sl_2,
            R.drawable.sl_3,
            R.drawable.sl_4
        )
        val sliderAdapter = SliderAdapter(images)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        sliderView.startAutoCycle()
    }

    private fun initBrandRecycler(view: View) {
        recyclerViewBrand = view.findViewById(R.id.brandRecyclerView)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewBrand.layoutManager = linearLayoutManager
        val brandList: MutableList<Brand> = ArrayList()
        brandList.add(Brand("brand_1", "Good Skyn"))
        // Add other brands here
        val customAdapter = BrandAdapter(requireContext(), brandList)
        recyclerViewBrand.adapter = customAdapter
    }

    private fun initCategory(view: View) {
        recyclerViewCat = view.findViewById(R.id.catRecyclerView)
        val glm = GridLayoutManager(requireContext(), 3)
        recyclerViewCat.layoutManager = glm
        val categories: MutableList<CategoriesModel> = ArrayList()
        categories.add(
            CategoriesModel(
                "cat_1",
                "Fitness Supplements",
                "Immunity boosters, multivitamins"
            )
        )
        // Add other categories here
        val customAdapter = HomeCatAdapter(requireContext(), categories)
        recyclerViewCat.adapter = customAdapter
    }
}
