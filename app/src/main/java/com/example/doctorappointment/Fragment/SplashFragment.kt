package com.example.doctorappointment.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.doctorappointment.Activity.MainActivity
import com.example.doctorappointment.R
import com.example.doctorappointment.ViewModel.AuthViewModel
import com.example.doctorappointment.databinding.ActivityMainBinding
import com.example.doctorappointment.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

private lateinit var binding:FragmentSplashBinding
    private var viewModel=AuthViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSplashBinding.inflate(layoutInflater)
        binding.stBtn.setOnClickListener {
        lifecycleScope.launch {
            viewModel.iscurrentuser.collect{
                if(it){
                    startActivity(Intent(requireContext(),MainActivity::class.java))
                    requireActivity().finish()
                }else{
                    findNavController().navigate(R.id.action_splashFragment_to_signinFragment)
                }
            }
        }


        }

        // Inflate the layout for this fragment
        return binding.root
    }

}