package com.example.doctorappointment.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorappointment.Adapter.NearDoctorAdapter
import com.example.doctorappointment.Model.DoctorsModel
import com.example.doctorappointment.R
import com.example.doctorappointment.ViewModel.MainViewModel
import com.example.doctorappointment.databinding.ActivitySeeAllBinding

class SeeAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeeAllBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel:MainViewModel by viewModels()
    private lateinit var adapter: NearDoctorAdapter
    private lateinit var list: MutableList<DoctorsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySeeAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.seeAllBackBtn.setOnClickListener {
           startActivity(Intent(this,MainActivity::class.java))
           finish()
       }
        recyclerView=binding.seeAllRecView
        recyclerView.hasFixedSize()
        viewModel.loadDoctors().observe(this){doctor->
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            adapter=NearDoctorAdapter(doctor)
            recyclerView.adapter=adapter



        }


    }

}