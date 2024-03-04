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
    var view: View? = null
    var recyclerView: RecyclerView? = null
    var searchBarText: EditText? = null
    var sliderView: SliderView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home, container, false)
        initBrandRecycler()
        initSlider()
        initCategory()
        searchBarText = view.findViewById(R.id.searchBar)
        searchBarText.setOnClickListener(View.OnClickListener {
            val activity = context as AppCompatActivity?
            val args = Bundle()
            val fm: Fragment = SearchFragment()
            fm.arguments = args
            activity!!.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.nav_host_fragment_activity_home, fm).commit()
        })
        return view
    }

    private fun initSlider() {
        sliderView = view!!.findViewById(R.id.imageSlider)
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

    private fun initBrandRecycler() {
        recyclerView = view!!.findViewById(R.id.brandRecyclerView)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation =
            LinearLayoutManager.HORIZONTAL // set Horizontal Orientation
        recyclerView.setLayoutManager(linearLayoutManager)
        val brandList: MutableList<Brand> = ArrayList()
        brandList.add(Brand("brand_1", "Good Skyn"))
        brandList.add(Brand("brand_2", "Huggies"))
        brandList.add(Brand("brand_3", "Glucon - D"))
        brandList.add(Brand("brand_4", "LivEasy"))
        brandList.add(Brand("brand_5", "Himalayas"))
        val customAdapter = BrandAdapter(context!!, brandList)
        recyclerView.setAdapter(customAdapter)
    }

    private fun initCategory() {
        recyclerView = view!!.findViewById(R.id.catRecyclerView)
        val glm = GridLayoutManager(context, 3)
        recyclerView.setLayoutManager(glm)
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
        val customAdapter = HomeCatAdapter(context!!, categories)
        recyclerView.setAdapter(customAdapter)
    }
}