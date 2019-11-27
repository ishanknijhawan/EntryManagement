package com.ishank.innovaccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {

    companion object {
        val hostName = ""
        val hostEmail =""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val db = FirebaseFirestore.getInstance()

        db.collection("Host")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.id.equals(document.getString("email"))){

                        et_h_name.setText(document.getString("name"))
                        et_h_email.setText(document.getString("email"))
                        et_h_phone.setText(document.getString("phoneNumber"))
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
            }

        btnDone.setOnClickListener {
            db.collection("Host").document(et_h_email.text.toString())
                .set(HostData(et_h_name.text.toString(),
                    et_h_email.text.toString(),et_h_phone.text.toString()))

            val intent = Intent(this, FrontPageActivity::class.java)
            startActivity(intent)
        }

    }
}
