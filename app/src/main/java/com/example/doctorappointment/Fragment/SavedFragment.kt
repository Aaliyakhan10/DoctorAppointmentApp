package com.example.doctorappointment.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorappointment.Activity.DetailActivity
import com.example.doctorappointment.Adapter.DoctorSavedAdapter
import com.example.doctorappointment.Adapter.NearDoctorAdapter

import com.example.doctorappointment.ViewModel.MainViewModel
import com.example.doctorappointment.databinding.ActivityDetailBinding
import com.example.doctorappointment.databinding.FragmentSavedBinding
import com.example.doctorappointment.roomDb.DoctorsSaved


class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var savedAdapter: DoctorSavedAdapter
    private lateinit var savedDoctorsList: List<DoctorsSaved>

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater)


        initNearByDoctors()

        return binding.root
    }

    private fun initNearByDoctors() {
       binding.proBar.visibility=View.VISIBLE
        // Initialize RecyclerView and adapter
        recyclerView = binding.savedReView
        recyclerView.setHasFixedSize(true)
      savedAdapter= DoctorSavedAdapter()
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = savedAdapter
        viewModel.getSavedDoctor().observe(requireActivity()){
            binding.proBar.visibility=View.GONE
            savedDoctorsList=it
            savedAdapter.differ.submitList(savedDoctorsList)
        }

    }




}
