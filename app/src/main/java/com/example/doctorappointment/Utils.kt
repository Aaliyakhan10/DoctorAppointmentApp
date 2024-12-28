package com.example.doctorappointment

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.doctorappointment.databinding.ProgessBarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Utils {
    private lateinit var firebasereference:DatabaseReference
    private var dialog:AlertDialog?=null
    fun showDialog(context: Context, message:String){
        val progress =ProgessBarBinding.inflate(LayoutInflater.from(context))
        progress.tvMessage.text=message
        dialog=AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()

    }
  fun hideDialog(){
      dialog?.dismiss()
  }
    fun userInfo(callback: (String, String, String, String, String) -> Unit) {
        val uid = getCurrentUsersId()
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("AllUsers").child("user")

        firebaseDatabase.child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val name = it.child("Name").value.toString()
                val number=it.child("phoneNumber").value.toString()
                val age=it.child("Age").value.toString()
                val sex=it.child("sex").value.toString()
                val add=it.child("Add").value.toString()
                callback(name,number,age,sex,add)
            } else {
                callback("People","","","","")
            }
        }.addOnFailureListener {
            callback("People","","","","")
        }
    }

    fun showTooast(context: Context,message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
    private var firebaseAuth:FirebaseAuth?= null
    fun getInstance():FirebaseAuth{
        if(firebaseAuth==null){
            firebaseAuth= FirebaseAuth.getInstance()
        }
        return firebaseAuth!!
    }
    fun getCurrentUsersId():String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}