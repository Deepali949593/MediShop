package com.example.medishop.Session

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.medishop.Activities.HomeActivity
import com.example.medishop.Activities.LoginActivity
import com.example.medishop.RoomDB.CartDb.Cart
import com.example.medishop.RoomDB.CartDb.CartDatabase
import com.example.medishop.RoomDB.CategoriesDb.Categories
import com.example.medishop.RoomDB.CategoriesDb.CategoriesDao
import com.example.medishop.RoomDB.CategoriesDb.CategoriesDatabase
import com.example.medishop.RoomDB.ProductDb.Product
import com.example.medishop.RoomDB.ProductDb.ProductDao
import com.example.medishop.RoomDB.ProductDb.ProductDatabase

class SessionManager(var _context: Context) {
    private val prefs: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    private var categoriesDatabase: CategoriesDatabase? = null
    private var categoriesDAO: CategoriesDao? = null
    private var productDatabase: ProductDatabase? = null
    private var productDao: ProductDao? = null

    init {
        // TODO Auto-generated constructor stub
        prefs = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = prefs.edit()
    }

    fun createLoginSession(email: String, name: String, phone: String) {
        // Storing login value
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PHONE, phone)
        editor.commit()
        Log.d("TAG", "createLoginSession: $email$name$phone")
        addCategories()
    }

    fun createLoginUpdateSession(email: String, name: String, phone: String) {
        // Storing login value
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PHONE, phone)
        editor.commit()
        Log.d("TAG", "createLoginSession: $email$name$phone")
    }

    private fun addCategories() {
        categoriesDatabase = CategoriesDatabase.getInstance(_context)
        categoriesDAO = categoriesDatabase.dao
        categoriesDAO.insertCategories(Categories("C001", "Brushes_cat", "brushes_cat"))
        categoriesDAO.insertCategories(Categories("C002", "Paints_cat", "paint_cat"))
        categoriesDAO.insertCategories(Categories("C003", "Stationary_cat", "stationary_cat"))
        categoriesDAO.insertCategories(Categories("C004", "Craft_cat", "craft_cat"))
        categoriesDAO.insertCategories(Categories("C005", "Canvas_cat", "canvas_cat"))
        categoriesDAO.insertCategories(Categories("C006", "Sketching_cat", "sketching_cat"))
        categoriesDAO.insertCategories(Categories("C007", "Journal_cat", "journal_cat"))
        Log.d("TAG", "addCategories: " + categoriesDAO.allCategories)
        productDatabase = ProductDatabase.getInstance(_context)
        productDao = productDatabase.dao
        val cartDatabase = CartDatabase.getInstance(_context)
        val cartDao = cartDatabase.dao
        cartDao.insertCart(
            Cart(
                "P001",
                "Cofsils Orange",
                "p_1",
                "32",
                "Lozenges Strip of 10",
                "4",
                "1"
            )
        )
        productDao.insertProduct(
            Product(
                "P001",
                "Cofsils Orange",
                "p_1",
                "C003",
                "32",
                "Lozenges Strip of 10",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P002",
                "Vicks Vaporub",
                "p_2",
                "C003",
                "165",
                "Vicks Vaporub 50 ml relief from cold cough headache and body pain",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P003",
                "Otrivin Baby Saline",
                "p_3",
                "C003",
                "59",
                "Otrivin Baby saline bottle of 10 ml nasal spray",
                "5"
            )
        )
        productDao.insertProduct(
            Product(
                "P004",
                "Mamaearth Bye Bye Blemishes Cream",
                "p_4",
                "C003",
                "500",
                "Mamaearth Bye Bye Blemishes Face Cream With Mulberry Extract & Vitamin C for Pigmentation and Blemish removal (30ml)",
                "4.5"
            )
        )
        productDao.insertProduct(
            Product(
                "P005",
                "Minimalist Salicylic Acid",
                "p_5",
                "C003",
                "549",
                "Minimalist Salicylic Acid 2% Serum for Blackheads, Pore Tightening, Oil Control & Acne Breakouts (30ml)",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P006",
                "Liveasy Adult Diaper",
                "p_6",
                "C003",
                "350",
                "Liveasy Adult Diaper Tape Style (M)-10|Anti Bacterial And Odour Lock Technology - Waist 28-45 Inch",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P007",
                "Liveasy Care Ultra Sanitary Pads",
                "p_7",
                "C004",
                "120",
                "Liveasy Care Ultra Sanitary Pads | 14 Xxl Pads | Soft & Rash Free | Leak-Lock Technology",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P008",
                "Prega News Pregnancy Test Kit",
                "p_8",
                "C004",
                "60",
                "Prega News Pregnancy Test Kit, (50 Pieces)",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P009",
                "Durex Extra Time Condoms",
                "p_9",
                "C004",
                "300",
                "Pack of 10",
                "5"
            )
        )
        productDao.insertProduct(
            Product(
                "P0010",
                "Everherb Karela Jamun Juice",
                "p_10",
                "C004",
                "230",
                "Helps Maintains Healthy Sugar Levels -Helps In Weight Management - 1l",
                "4.5"
            )
        )
        productDao.insertProduct(
            Product(
                "P0011",
                "Volini",
                "p_11",
                "C004",
                "490",
                "Volini Pain Relief Gel Tube Of 75 G",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P0012",
                "Nestle Nan Pro",
                "p_12",
                "C004",
                "900",
                "Nestle Nan Pro Stage 1 Infant Formula (Upto 6 Months) - 400 G Bag-In-Box",
                "4"
            )
        )
    }

    fun addProducts() {
        productDatabase = ProductDatabase.getInstance(_context)
        productDao = productDatabase.dao
        val cartDatabase = CartDatabase.getInstance(_context)
        val cartDao = cartDatabase.dao
        cartDao.insertCart(
            Cart(
                "P001",
                "Cofsils Orange",
                "p_1",
                "32",
                "Lozenges Strip of 10",
                "4",
                "1"
            )
        )
        productDao.insertProduct(
            Product(
                "P001",
                "Cofsils Orange",
                "p_1",
                "C003",
                "32",
                "Lozenges Strip of 10",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P002",
                "Vicks Vaporub",
                "p_2",
                "C003",
                "165",
                "Vicks Vaporub 50 ml relief from cold cough headache and body pain",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P003",
                "Otrivin Baby Saline",
                "p_3",
                "C003",
                "59",
                "Otrivin Baby saline bottle of 10 ml nasal spray",
                "5"
            )
        )
        productDao.insertProduct(
            Product(
                "P004",
                "Mamaearth Bye Bye Blemishes Cream",
                "p_4",
                "C003",
                "500",
                "Mamaearth Bye Bye Blemishes Face Cream With Mulberry Extract & Vitamin C for Pigmentation and Blemish removal (30ml)",
                "4.5"
            )
        )
        productDao.insertProduct(
            Product(
                "P005",
                "Minimalist Salicylic Acid",
                "p_5",
                "C003",
                "549",
                "Minimalist Salicylic Acid 2% Serum for Blackheads, Pore Tightening, Oil Control & Acne Breakouts (30ml)",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P006",
                "Liveasy Adult Diaper",
                "p_6",
                "C003",
                "350",
                "Liveasy Adult Diaper Tape Style (M)-10|Anti Bacterial And Odour Lock Technology - Waist 28-45 Inch",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P007",
                "Liveasy Care Ultra Sanitary Pads",
                "p_7",
                "C004",
                "120",
                "Liveasy Care Ultra Sanitary Pads | 14 Xxl Pads | Soft & Rash Free | Leak-Lock Technology",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P008",
                "Prega News Pregnancy Test Kit",
                "p_8",
                "C004",
                "60",
                "Prega News Pregnancy Test Kit, (50 Pieces)",
                "4"
            )
        )
        productDao.insertProduct(
            Product(
                "P009",
                "Durex Extra Time Condoms",
                "p_9",
                "C004",
                "300",
                "Pack of 10",
                "5"
            )
        )
        productDao.insertProduct(
            Product(
                "P0010",
                "Everherb Karela Jamun Juice",
                "p_10",
                "C004",
                "230",
                "Helps Maintains Healthy Sugar Levels -Helps In Weight Management - 1l",
                "4.5"
            )
        )
        productDao.insertProduct(
            Product(
                "P0011",
                "Volini",
                "p_11",
                "C004",
                "490",
                "Volini Pain Relief Gel Tube Of 75 G",
                "3"
            )
        )
        productDao.insertProduct(
            Product(
                "P0012",
                "Nestle Nan Pro",
                "p_12",
                "C004",
                "900",
                "Nestle Nan Pro Stage 1 Infant Formula (Upto 6 Months) - 400 G Bag-In-Box",
                "4"
            )
        )
    }

    fun checkLogin() {
        // Check login status
        if (!isLoggedIn) {
            val i = Intent(_context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            _context.startActivity(i)
        } else {
            val intent = Intent(_context, HomeActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            _context.startActivity(intent)
        }
    }

    val userDetails: HashMap<String, String?>
        get() {
            val user = HashMap<String, String?>()
            user[KEY_EMAIL] = prefs.getString(KEY_EMAIL, null)
            user[KEY_NAME] =
                prefs.getString(KEY_NAME, null)
            user[KEY_PHONE] =
                prefs.getString(KEY_PHONE, null)
            return user
        }

    //  Clear session details
    fun logoutUser() {
        editor.clear()
        editor.commit()
        CartDatabase.getInstance(_context).dao.deleteAll()
        CategoriesDatabase.getInstance(_context).dao.deleteAll()
        ProductDatabase.getInstance(_context).dao.deleteAll()
        val i = Intent(_context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        _context.startActivity(i)
        (_context as Activity).finish()
    }

    val isLoggedIn: Boolean
        //Get Login State
        get() = prefs.getBoolean(IS_LOGIN, false)

    companion object {
        private const val PREF_NAME = "Colour_Trails"
        private const val IS_LOGIN = "IsLoggedIn"
        const val KEY_EMAIL = "Email"
        const val KEY_NAME = "Name"
        const val KEY_PHONE = "Phone"
    }
}
