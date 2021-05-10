package com.example.api_notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
//import com.android.volley.AuthFailureError
//import com.android.volley.Request
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.json.JSONObject
import java.util.*

class Sign_in : AppCompatActivity() {
    var firstName: EditText? = null
    var secondName: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var signupButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        firstName = findViewById<View>(R.id.firstName) as EditText
        secondName = findViewById<View>(R.id.secondName) as EditText
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.pass) as EditText
        signupButton = findViewById<View>(R.id.login) as Button
        signupButton!!.setOnClickListener {

            Submit()
            var i = Intent(this, Signup::class.java)
            startActivity(i)
        }

        accountSignIn.setOnClickListener {
            var i = Intent(this, Signup::class.java)
            startActivity(i)
        }
    }

    private fun Submit() {
        val url = "https://mcc-users-api.herokuapp.com/add_new_user"
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                    val data = JSONObject(response)
                    Log.e("TAG", "$data")

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "error ")


            }) {
            override fun getParams(): MutableMap<String, String> {
                var params = HashMap<String, String>()
                params["firstName"] = firstName!!.text.toString()
                params["secondName"] = secondName!!.text.toString()
                params["email"] = email!!.text.toString()
                params["password"] = password!!.text.toString()
                return params
            }

        }



        queue.add(request)

    }
}
