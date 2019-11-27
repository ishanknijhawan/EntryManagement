package com.ishank.innovaccer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.activity_main.*

class HostActivity : AppCompatActivity() {

    companion object {
        var hostName = ""
        var hostEmail =""
        var hostPhone =""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        val db = FirebaseFirestore.getInstance()

        db.collection("Host")
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    if (document.id.equals(hostEmail)){

                        et_h_name.setText(document.getString("name"))
                        et_h_email.setText(document.getString("email"))
                        et_h_phone.setText(document.getString("phoneNumber"))
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
            }

        btnDone.setOnClickListener {

            if (et_h_email.text.toString().isEmpty() || et_h_name.text.toString().isEmpty() || et_h_phone.text.toString().isEmpty())
                Snackbar.make(it,"Fields can't be empty",Snackbar.LENGTH_SHORT).show()


            else {
                Snackbar.make(it,"changes saved successfully",Snackbar.LENGTH_SHORT).show()
                db.collection("Host").document(et_h_email.text.toString())
                    .set(HostData(et_h_name.text.toString(),
                        et_h_email.text.toString(),et_h_phone.text.toString()))

                hostName = et_h_name.text.toString()
                hostEmail = et_h_email.text.toString()
                hostPhone = et_h_phone.text.toString()

                val intent = Intent(this, FrontPageActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
