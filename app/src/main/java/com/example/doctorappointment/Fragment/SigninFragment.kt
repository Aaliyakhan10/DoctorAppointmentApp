package com.example.doctorappointment.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.doctorappointment.Activity.MainActivity
import com.example.doctorappointment.Model.Users
import com.example.doctorappointment.R
import com.example.doctorappointment.Utils
import com.example.doctorappointment.ViewModel.AuthViewModel
import com.example.doctorappointment.databinding.FragmentSigninBinding
import com.google.firebase.FirebaseException
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch


class SigninFragment : Fragment() {
    private var viewModel=AuthViewModel()

   
private lateinit var binding: FragmentSigninBinding
    private lateinit var otp: String
    private lateinit var number: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSigninBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        getusernumber()
        onSendButtonClick()

        onClickConfirmOptButton()
        checkOtp()


        return binding.root
    }




    private fun getusernumber() {
        binding.numTv.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                val len = number?.length
                if (len == 10) {
                    binding.sendBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.darkgreen
                        )
                    )
                } else {
                    binding.sendBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        )
    }
        private fun checkOtp(){
        binding.optTv.addTextChangedListener(object:TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                val len=number?.length
                if(len==6){
                    binding.confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.darkgreen))
                }else{
                    binding.confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        )
    }

    private fun onSendButtonClick() {
        binding.sendBtn.setOnClickListener {
            number =binding.numTv.text.toString()
            if(number.isEmpty() || number.length!=10){
                Toast.makeText(activity, "Enter valid Number", Toast.LENGTH_SHORT).show()

            }else{
                sendOpt()
            }
        }
    }
    private fun sendOpt() {
        Utils.showDialog(requireContext(),"Sending Opt..")
        viewModel.apply {

            sendOpt(number, requireActivity())
            lifecycleScope.launch {
                optsent.collect{
                    if(it){
                        Utils.hideDialog()
                        Toast.makeText(requireContext(),"Opt send",Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
        }

    private fun verifyopt() {
        val user= Users(null,number,null,null,null,null)
        viewModel.signInWithPhoneAuthCredential(otp, number,user)
        lifecycleScope.launch {
            viewModel.issigned.collect{
                if(it){
                    Utils.hideDialog()
                    Utils.showTooast(requireContext(),"Logged in")
                    val intent= Intent(requireContext(),MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                }
            }
        }

    }


    private fun onClickConfirmOptButton() {

        binding.confirmButton.setOnClickListener{
            otp=binding.optTv.text.toString()

            Utils.showDialog(requireContext(),"Signing you..")
            if(otp.length<6){
                Toast.makeText(requireContext(),"Enter right OPT",Toast.LENGTH_SHORT).show()
                Utils.hideDialog()
            }
            else{
                verifyopt()
            }
        }
    }

}

