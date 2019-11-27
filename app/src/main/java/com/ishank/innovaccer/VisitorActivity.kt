package com.ishank.innovaccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class VisitorActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    private var subject = ""
    private var message = ""
    private var hostEmail : String? = null
    private var sdf = SimpleDateFormat("dd/mm/yyyy HH:mm:ss")
    val date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db.collection("Host")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.id.equals(document.getString("email"))){
                        hostEmail = document.getString("email")
                    }
                    }
                }

//        btnCheckOut.setOnClickListener {
//            sendMail()
//            val intent = Intent(this, FrontPageActivity::class.java)
//            startActivity(intent)
//        }

        btnCheckIn.setOnClickListener {
            if (HostActivity.HostName.isNotEmpty())
            sendMailHost()
            val intent = Intent(this, CheckInActivity::class.java)
            intent.putExtra("name",et_v_name.text.toString())
            intent.putExtra("email",et_v_email.text.toString())
            intent.putExtra("phone",et_v_phone.text.toString())
            intent.putExtra("checkin",sdf.format(date))
            startActivity(intent)
        }

    }

    private fun sendMailHost() {
        subject = "Innovaccer"
        message = """
            Visitor Successfully checked In
            
            Name : ${et_v_name.text}
            Email : ${et_v_email.text}
            Phone : ${et_v_phone.text}
            Check-in time : ${sdf.format(date)}
        """.trimIndent()

        val javaMailAPI = JavaMailApi(this,hostEmail.toString(),subject,message)
        javaMailAPI.execute()
        Log.d("HOST","Host name is ${hostEmail.toString()}")
    }
}
