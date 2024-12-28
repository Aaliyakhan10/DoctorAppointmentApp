package com.example.doctorappointment.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorappointment.R
import com.example.doctorappointment.Utils
import com.example.doctorappointment.databinding.FragmentBmiBinding

class BmiFragment : Fragment() {
private lateinit var binding:FragmentBmiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBmiBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        onBmiCalculateButtonCLick()
        onRadioButton()
        return binding.root
    }

    private fun onRadioButton() {
        binding.radioGroupBtn.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.maleRadioBtn -> {
                    binding.maleRadioBtn.isChecked = true
                    binding.femaleradiBtn.isChecked = false
                    binding.femaleradiBtn.isEnabled = true
                    binding.maleRadioBtn.isEnabled = true
                }
                R.id.femaleradiBtn -> {
                    binding.femaleradiBtn.isChecked = true
                    binding.maleRadioBtn.isChecked = false
                    binding.maleRadioBtn.isEnabled = true
                    binding.femaleradiBtn.isEnabled = true
                }
            }
        }

    }


    private fun onBmiCalculateButtonCLick(){
        binding.calBmiBtn.setOnClickListener {
            val weight=binding.wieghtTv.text.toString()
            val height=binding.heightTv.text.toString()
            val age=binding.ageTv.text.toString()
            if(weight.isEmpty()||height.isEmpty()||age.isEmpty()){
                Utils.showTooast(requireContext(),"All fields are compulsory")
            }else if(!binding.maleRadioBtn.isChecked&&!binding.femaleradiBtn.isChecked){
                Utils.showTooast(requireContext(),"Select Gender")
            }
            else{
                val heightcm=(height.toInt())
                val heightm=heightcm/100.0

                val weight=weight.toInt()

                val bmi=(weight/(heightm*heightm))

                binding.resTv.text="Your BMI is  %.2f  kg/m2".format(bmi)
                if(bmi<18.7){

                    val gainWeight=18.7*heightm*heightm
                    val weightReq=(gainWeight-weight).toInt()

                    binding.requWeiTv.text="You need to gain $weightReq kgs to reach 18.7 kg/m2"
                }else if(bmi>26.3){

                    val LooseWeight=26.3* heightm*heightm
                    val weightReq=(LooseWeight-weight).toInt()

                    binding.requWeiTv.text="You need to loose $weightReq kgs to reach 26.3 kg/m2"

                }else {
                    binding.requWeiTv.text = "Your BMI is in the healthy range."
                }
                val bmipr=bmi/25
                binding.bmiPriTv.text="Your BMI prime is %.2f".format(bmipr)


            }
        }

    }


}