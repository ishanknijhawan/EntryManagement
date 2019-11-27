package com.ishank.innovaccer

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class VisitorActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()

    private var subject = ""
    private var message = ""
    private var sms = ""
    private var HostEmail : String? = null
    private var HostPhone : String? = null
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
                        HostEmail = document.getString("email")
                        HostPhone = document.getString("phoneNumber")
                    }
                    }
                }

        btnCheckIn.setOnClickListener {

//            val permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)
//            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
//                MyMessage()
//            else

            if (et_v_email.text.toString().isEmpty() || et_v_name.text.toString().isEmpty() || et_v_phone.text.toString().isEmpty())
            Snackbar.make(it,"Fields can't be empty",Snackbar.LENGTH_SHORT).show()

            else {
                MyMessage()
                //if (HostActivity.HostName.isNotEmpty())
                sendMailHost()
                val intent = Intent(this, CheckInActivity::class.java)
                intent.putExtra("name",et_v_name.text.toString())
                intent.putExtra("email",et_v_email.text.toString())
                intent.putExtra("phone",et_v_phone.text.toString())
                intent.putExtra("checkin",sdf.format(date))
                startActivity(intent)
            }
        }

    }

    private fun MyMessage() {
        sms = """
            Visitor Successfully checked In
            
            Name : ${et_v_name.text}
            Email : ${et_v_email.text}
            Phone : ${et_v_phone.text}
            Check-in time : ${sdf.format(date)}
        """.trimIndent()

        //val sentPI = PendingIntent()
        val smsManager = SmsManager.getDefault() as SmsManager
        val finalPhone = "+91${HostActivity.hostPhone}"

        smsManager.sendTextMessage(finalPhone,null,sms,null,null)
        Log.d("PHONE","phone number is ${HostActivity.hostPhone}")

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

        val javaMailAPI = JavaMailApi(this,HostActivity.hostEmail,subject,message)
        javaMailAPI.execute()
        Log.d("HOST","Host name is ${HostActivity.hostEmail}")
    }
}
