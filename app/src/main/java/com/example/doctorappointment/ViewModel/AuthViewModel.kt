package com.example.doctorappointment.ViewModel

import android.app.Activity
import com.example.doctorappointment.Model.Users
import com.example.doctorappointment.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel {
private  val _verificationId= MutableStateFlow<String?>(null)
    private val _otpSent= MutableStateFlow(false)
    private val _issigned= MutableStateFlow(false)
    private val _iscurrentuser= MutableStateFlow(false)
    val iscurrentuser=_iscurrentuser
    val issigned=_issigned
    val optsent=_otpSent
init {
    Utils.getInstance().currentUser?.let {
        _iscurrentuser.value=true
    }
}
    fun sendOpt(userNumber:String,activity:Activity){
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _verificationId.value=verificationId
                _otpSent.value=true

            }
        }
        val options = PhoneAuthOptions.newBuilder(Utils.getInstance())
            .setPhoneNumber("+91$userNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun signInWithPhoneAuthCredential(opt:String,userNumber: String,user: Users) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value.toString(), opt)
        Utils.getInstance().signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                user.uid=Utils.getCurrentUsersId()
                if (task.isSuccessful) {
                    FirebaseDatabase.getInstance().getReference("AllUsers").child("user").child(user.uid!!).setValue(user)
                    _issigned.value=true

                } else {

                }
            }
    }
}