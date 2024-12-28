package com.example.doctorappointment.Fragment

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.room.util.recursiveFetchHashMap
import com.example.doctorappointment.Activity.SplashActivity
import com.example.doctorappointment.R
import com.example.doctorappointment.Utils
import com.example.doctorappointment.Utils.userInfo
import com.example.doctorappointment.databinding.FragmentAccountBinding
import com.example.doctorappointment.databinding.InfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AccountFragment : Fragment() {
    private lateinit var firebaseDatabase: DatabaseReference
   private lateinit var binding: FragmentAccountBinding

   private var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var gender:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAccountBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        usernameDisplay()
        onLogoutButtonclicked()
        onDetailButtonClicked()
        onRadioButton()

        return binding.root
    }

    private fun usernameDisplay() {
        userInfo { name, number, age, sex, add ->
            val Name=name
            val Number=number
            val Age=age
            val Address=add
            val Sex=sex

            if(!(Sex=="null")){
                binding.userNameTVAc.text = "Hi," + Name
                binding.detailsBtn.text="Update your details"
                binding.ageTvacc.text=Age
                binding.addTv.text=Address
                binding.sexTv.text=Sex

            }
            binding.phoNumTv.text = Number

                var detail=InfoBinding.inflate(layoutInflater)
                detail.username.hint=Name
                detail.userAdd.hint=Address
                detail.userAge.hint= Age



            }

    }

    private fun onDetailButtonClicked() {
        binding.detailsBtn.setOnClickListener {

            var detail=InfoBinding.inflate(layoutInflater)
            val dialog=AlertDialog.Builder(requireContext())
                .setView(detail.root)
                .create()
            dialog.show()
            detail.saveBtn.setOnClickListener {


                val Address=detail.userAdd.text.toString()
                val age=detail.userAge.text.toString()
                val name=detail.username.text.toString()

             if(detail.femaleradiBtnacc.isChecked){
                 gender="Female"
             }else if(detail.maleRadioBtnacc.isChecked){
                 gender="Male"
             }
                if(Address.isEmpty()||age.isEmpty()||name.isEmpty()){
                    Utils.showTooast(requireContext(),"All fields are mandatory")

                }else if(!detail.femaleradiBtnacc.isChecked&&!detail.maleRadioBtnacc.isChecked){
                    Utils.showTooast(requireContext(),"Select Gender")
                }
                else{
                      update(name,age,gender,Address)

                    dialog.dismiss()

                }

            }
        }

    }



    private fun update(name:String,age:String,sex:String,Add:String){
         firebaseDatabase = FirebaseDatabase.getInstance().getReference("AllUsers")
        val uid=Utils.getCurrentUsersId()
        val data = mapOf<String,String>("Name" to name,"Age" to age,"sex" to sex ,"Add" to Add)
        firebaseDatabase.child("user").child(uid).updateChildren(data).addOnSuccessListener {
            Utils.showTooast(requireContext(),"Details saved")
        }
    }

    private fun onLogoutButtonclicked() {
        binding.logoutBtn.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    // Sign out from Firebase
                    firebaseAuth.signOut()
                    // Start the SplashActivity and finish the current activity
                    startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    requireActivity().finish()
                }
                .setNeutralButton("No") { dialog, _ ->
                    dialog.dismiss() // Dismiss the dialog if "No" is clicked
                }
                .create()

            dialog.show()
        }

    }
    private fun onRadioButton(){
        val bindingDetail=InfoBinding.inflate(layoutInflater)

        bindingDetail.radioGroupBtnacc.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.maleRadioBtn -> {
                    bindingDetail.maleRadioBtnacc.isChecked = true
                    bindingDetail.femaleradiBtnacc.isChecked = false
                    bindingDetail.femaleradiBtnacc.isEnabled = true
                    bindingDetail.maleRadioBtnacc.isEnabled = true


                }
                R.id.femaleradiBtn -> {
                    bindingDetail.femaleradiBtnacc.isChecked = true
                    bindingDetail.maleRadioBtnacc.isChecked = false
                    bindingDetail.maleRadioBtnacc.isEnabled = true
                    bindingDetail.femaleradiBtnacc.isEnabled = true

                }
            }
        }



    }


}