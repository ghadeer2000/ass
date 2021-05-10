package com.example.api_notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Signup : AppCompatActivity() {
    private lateinit var token: String
    lateinit private var email: String
    lateinit private var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        token = ""
        accountSignup.setOnClickListener {
            var i = Intent(this, Sign_in::class.java)
            startActivity(i)
        }
        signup.setOnClickListener {
            if (signemail.text.isNotEmpty() && signemail.text.toString() != "" && signpass.text.isNotEmpty() && signpass.text.toString() != "") {
                email = signemail.text.toString()
                password = signpass.text.toString()

            }
            getToken()
            checkEmailAndPassword(email, password)

        }


    }

    private fun checkEmailAndPassword(email: String, password: String) {
        val url = "https://mcc-users-api.herokuapp.com/login"
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url, Response.Listener { response ->
            Log.e("TAG login", response)
            try {
                var i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } catch (e: JSONException) {
                Log.d("TAG", "Server Error ")
                Toast.makeText(this, "Invaild user", Toast.LENGTH_SHORT).show()
            }


        }, Response.ErrorListener { error ->
            Log.e("TAG error", error.toString())
        }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String, String>()
                hashMap["email"] = email
                hashMap["password"] = password

                addUserRegistrationToken(email, password, token)
                return hashMap
            }
        }
        queue.add(request)
    }

    private fun addUserRegistrationToken(email: String, password: String, regToken: String) {
        val url = "https://mcc-users-api.herokuapp.com/add_reg_token"
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.PUT, url, Response.Listener { response ->
            Log.e("TAG add_reg_token", response)

        }, Response.ErrorListener { error ->
            Log.e("TAG error", "error")
        }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String, String>()
                hashMap["email"] = email
                hashMap["password"] = password
                hashMap["reg_token"] = regToken
                return hashMap
            }
        }
        queue.add(request)
    }


    fun getToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                }
                token = task.result.toString()
            }
    }
}




