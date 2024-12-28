package com.example.doctorappointment.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorappointment.Model.DoctorsModel
import com.example.doctorappointment.ViewModel.MainViewModel
import com.example.doctorappointment.databinding.ActivityDetailBinding
import com.example.doctorappointment.roomDb.DoctorsSaved
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import kotlinx.coroutines.flow.MutableStateFlow

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: MainViewModel by viewModels() // ViewModel initialization
    private var _isSaved= MutableStateFlow(false)
    private var issaved=_isSaved
    private lateinit var doctorSavedLists:List<DoctorsSaved>

    private lateinit var retrievedItem: DoctorsModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Retrieve passed data
        retrievedItem = intent.getParcelableExtra<DoctorsModel>("Object") ?: return

        getbundle()
        toHandleksavedButton()
        binding.bookmark.setOnClickListener {
            lifecycleScope.launch {
                savedDoctors(retrievedItem)
                _isSaved.value = true  // Update state immediately
                toHandleksavedButton()  // Recheck saved status
            }
        }

        binding.bookmarkSaved.setOnClickListener {
            lifecycleScope.launch {
                deleteSavedDoctors()
                _isSaved.value = false  // Update state immediately
                toHandleksavedButton()  // Recheck saved status
            }
        }


    }

    private fun toHandleksavedButton() {
        lifecycleScope.launch {
            val savedDoctor = viewModel.getSavedDoctorById(retrievedItem.Id)
            if (savedDoctor != null) {
                binding.bookmark.visibility = View.GONE
                binding.bookmarkSaved.visibility = View.VISIBLE
            } else {
                binding.bookmark.visibility = View.VISIBLE
                binding.bookmarkSaved.visibility = View.GONE
            }
        }
    }


    private fun deleteSavedDoctors() {
        lifecycleScope.launch {
            viewModel.deletesaved(retrievedItem.Id)
        }
        _isSaved.value=false

    }

    // Function to save doctor information
    private fun savedDoctors(retrievedItem: DoctorsModel) {
        val doctorInfo = DoctorsSaved(
            Id = retrievedItem.Id,
            Address = retrievedItem.Address,
            Biography = retrievedItem.Biography,
            Name = retrievedItem.Name,
            Patiens = retrievedItem.Patiens,
            Picture = retrievedItem.Picture,
            Expriense = retrievedItem.Expriense,
            Cost = retrievedItem.Cost,
            Date = retrievedItem.Date,
            Time = retrievedItem.Time,
            Special = retrievedItem.Special,
            Rating = retrievedItem.Rating,
            site = retrievedItem.site,
            Mobile = retrievedItem.Mobile,
            Location = retrievedItem.Location
        )
        // Call the ViewModel method to save doctor info
        lifecycleScope.launch {
            viewModel.savedDoctors(doctorInfo)
        }
    }

    // Function to set up the bundle data
    private fun getbundle() {
        binding.apply {
            speTxt.text = retrievedItem.Special
            nametxt.text = retrievedItem.Name
            ptnTxt.text = retrievedItem.Patiens
            bioTxt.text = retrievedItem.Biography
            dateTxt.text = retrievedItem.Date
            timeTxt.text = retrievedItem.Time
            addTxt.text = retrievedItem.Address
            expTxt.setText("${retrievedItem.Expriense} Years")
            rateTxt.text = "${retrievedItem.Rating}"

            // Back button functionality
            backBtn.setOnClickListener {
                startActivity(Intent(this@DetailActivity, MainActivity::class.java))
            }

            // SMS functionality
            meeTxt.setOnClickListener {
                val uri = Uri.parse("smsto:${retrievedItem.Mobile}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", "The Sms text")
                startActivity(intent)
            }

            // Phone call functionality
            phoneTxt.setOnClickListener {
                val uri = Uri.parse("tel:${retrievedItem.Mobile}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            }

            // Image loading using Glide
            try {
                Glide.with(this@DetailActivity)
                    .load(retrievedItem.Picture)
                    .apply(RequestOptions().centerCrop())
                    .into(img)
            } catch (e: Exception) {
                Log.e("DetailActivity", "Error loading image", e)
            }
        }
    }
}
