package com.example.doctorappointment.Fragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorappointment.Activity.SeeAllActivity
import com.example.doctorappointment.Adapter.NearDoctorAdapter
import com.example.doctorappointment.Utils.userInfo
import com.example.doctorappointment.ViewModel.MainViewModel
import com.example.doctorappointment.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var nearDoctorAdapter: NearDoctorAdapter

    // Initialize ViewModel using activityViewModels or ViewModelProvider
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize Nearby Doctors
        initNearByDoctors()

        // Display Username
        userNameDisplay()
        onSeeAllButtonClicked()
        return binding.root
    }

    private fun onSeeAllButtonClicked() {
        binding.seeAllTv.setOnClickListener {
         startActivity(Intent(requireContext(),SeeAllActivity::class.java))
        }
    }


    private fun userNameDisplay() {
        userInfo { name, number, age, sex, add ->

                binding.usernameHome.text = "Hi, $name"



        }
    }

    private fun initNearByDoctors() {
        nearDoctorAdapter = NearDoctorAdapter(mutableListOf())

        binding.apply {
            progressBar.visibility = View.VISIBLE

            // Observe LiveData using viewLifecycleOwner instead of 'this'
            viewModel.loadDoctors().observe(viewLifecycleOwner, Observer { doctors ->
                recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = NearDoctorAdapter(doctors)
                progressBar.visibility = View.GONE
            })
        }
    }
}
