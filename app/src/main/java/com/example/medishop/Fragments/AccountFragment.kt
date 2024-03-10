package com.example.medishop.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.medishop.R
import com.example.medishop.RoomDB.UserDb.User
import com.example.medishop.RoomDB.UserDb.UserDAO
import com.example.medishop.RoomDB.UserDb.UserDatabase
import com.example.medishop.Session.SessionManager

class AccountFragment : Fragment(), View.OnClickListener {
    private var nameEditText: EditText? = null
    private var phoneEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var updateButton: AppCompatButton? = null
    private var editProfileButton: AppCompatButton? = null
    private var logoutButton: AppCompatButton? = null
    private var sessionManager: SessionManager? = null
    private var userDatabase: UserDatabase? = null
    private var userDAO: UserDAO? = null
    private var user: HashMap<String, String?> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        initUI(view)
        return view
    }

    private fun initUI(view: View) {
        nameEditText = view.findViewById(R.id.nameEditText)
        phoneEditText = view.findViewById(R.id.phoneEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        updateButton = view.findViewById(R.id.updateButton)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        logoutButton = view.findViewById(R.id.logoutButton)

        nameEditText?.isEnabled = false
        phoneEditText?.isEnabled = false
        emailEditText?.isEnabled = false
        logoutButton?.isEnabled = true
        updateButton?.visibility = View.INVISIBLE

        updateButton?.setOnClickListener(this)
        editProfileButton?.setOnClickListener(this)
        logoutButton?.setOnClickListener(this)

        sessionManager = SessionManager(requireContext())
        user = sessionManager?.userDetails ?: HashMap<String, String?>()
        nameEditText?.setText(user[SessionManager.KEY_NAME])
        phoneEditText?.setText(user[SessionManager.KEY_PHONE])
        emailEditText?.setText(user[SessionManager.KEY_EMAIL])

        userDatabase = UserDatabase.getInstance(requireContext())
        userDAO = userDatabase?.dao
    }

    override fun onClick(view: View) {
        // onClick implementation remains the same
    }
}
