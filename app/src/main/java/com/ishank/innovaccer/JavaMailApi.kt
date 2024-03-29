package com.ishank.innovaccer

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class JavaMailApi //Constructor
    (
    //Add those line in dependencies
//implementation files('libs/activation.jar')
//implementation files('libs/additionnal.jar')
//implementation files('libs/mail.jar')
//Need INTERNET permission
//Variables
    private val mContext: Context,
    private val mEmail: String,
    private val mSubject: String,
    private val mMessage: String
) :
    AsyncTask<Void?, Void?, Void?>() {
    private var mSession: Session? = null
    private var mProgressDialog: ProgressDialog? = null
    override fun onPreExecute() {
        super.onPreExecute()
        //Show progress dialog while sending email
        mProgressDialog =
            ProgressDialog.show(mContext, "Loading", "Please wait...", false, false)
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        //Dismiss progress dialog when message successfully send
        mProgressDialog!!.dismiss()
        //Show success toast
        //Toast.makeText(mContext, "Checked out successfully", Toast.LENGTH_SHORT).show()
    }

     override fun doInBackground(vararg params: Void?): Void? { //Creating properties
        val props = Properties()
        //Configuring properties for gmail
//If you are not using gmail you may need to change the values
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.socketFactory.port"] = "465"
        props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.port"] = "465"
        //Creating a new session
        mSession = Session.getDefaultInstance(props,
            object : Authenticator() {
                //Authenticating the password
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD)
                }
            })
        try { //Creating MimeMessage object
            val mm = MimeMessage(mSession)
            //Setting sender address
            mm.setFrom(InternetAddress(Utils.EMAIL))
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, InternetAddress(mEmail))
            //Adding subject
            mm.subject = mSubject
            //Adding message
            mm.setText(mMessage)
            //Sending email
            Transport.send(mm)
            //            BodyPart messageBodyPart = new MimeBodyPart();
//
//            messageBodyPart.setText(message);
//
//            Multipart multipart = new MimeMultipart();
//
//            multipart.addBodyPart(messageBodyPart);
//
//            messageBodyPart = new MimeBodyPart();
//
//            DataSource source = new FileDataSource(filePath);
//
//            messageBodyPart.setDataHandler(new DataHandler(source));
//
//            messageBodyPart.setFileName(filePath);
//
//            multipart.addBodyPart(messageBodyPart);
//            mm.setContent(multipart);
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
        return null
    }

}