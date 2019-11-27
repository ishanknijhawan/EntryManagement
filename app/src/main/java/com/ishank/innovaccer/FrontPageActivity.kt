package com.ishank.innovaccer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_front_page.*
import java.text.SimpleDateFormat
import java.util.*

class FrontPageActivity : AppCompatActivity() {
    private var sdf = SimpleDateFormat("dd/mm/yyyy HH:mm:ss")
    val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_page)

        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.SEND_SMS
            ),
            111
        )

        visitor_login.setOnClickListener {
            val intent = Intent(this,VisitorActivity::class.java)
            startActivity(intent)
        }

        host_details.setOnClickListener {
            val intent = Intent(this,HostActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if ((grantResults[0] == PackageManager.PERMISSION_DENIED) ) {
            Toast.makeText(this, "can't run without permissions", Toast.LENGTH_SHORT).show()
        }
//        else
//            MyMessage()

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}
