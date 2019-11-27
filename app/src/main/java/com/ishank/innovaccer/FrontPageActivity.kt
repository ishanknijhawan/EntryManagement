package com.ishank.innovaccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_front_page.*
import java.text.SimpleDateFormat
import java.util.*

class FrontPageActivity : AppCompatActivity() {
    private var sdf = SimpleDateFormat("dd/mm/yyyy HH:mm:ss")
    val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_page)

        visitor_login.setOnClickListener {
            val intent = Intent(this,VisitorActivity::class.java)
            startActivity(intent)
        }

        host_details.setOnClickListener {
            val intent = Intent(this,HostActivity::class.java)
            startActivity(intent)
        }
    }
}
