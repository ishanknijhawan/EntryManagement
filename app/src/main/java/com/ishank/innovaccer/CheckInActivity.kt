package com.ishank.innovaccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class CheckInActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private var subject = ""
    private var message = ""
    private var sdf = SimpleDateFormat("dd/mm/yyyy HH:mm:ss")
    val date = Date()
    private var hostName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        db.collection("Host")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.id.equals(document.getString("email"))){
                        hostName = document.getString("name")
                    }
                }
            }

        btnCheckOut.setOnClickListener {
            sendMailVisitor()
            val intent = Intent(this, FrontPageActivity::class.java)
            startActivity(intent)
        }

    }

    private fun sendMailVisitor() {
        subject = "Innovaccer"
        message = """
            Successfully checked out from Innovacer
            
            Name : ${intent.getStringExtra("name")}
            Phone : ${intent.getStringExtra("phone")}
            Check-in time : ${intent.getStringExtra("checkin")}
            Check-out time : ${sdf.format(date)}
            Host name : ${hostName.toString()}
            Address visited :
        """.trimIndent()

        val javaMailAPI = JavaMailApi(this,intent.getStringExtra("email"),subject,message)
        javaMailAPI.execute()
    }
}
