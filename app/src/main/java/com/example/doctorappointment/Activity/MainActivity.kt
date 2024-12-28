package com.example.doctorappointment.Activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.doctorappointment.Fragment.AccountFragment
import com.example.doctorappointment.Fragment.HomeFragment

import com.example.doctorappointment.Fragment.BmiFragment
import com.example.doctorappointment.Fragment.SavedFragment
import com.example.doctorappointment.R
import com.example.doctorappointment.databinding.ActivityMainBinding
import com.example.doctorappointment.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bottomNavigationView = binding.bottomNav

        // Set initial fragment to HomeFragment
        replaceFragment(HomeFragment())


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.bookmark -> {
                    replaceFragment(SavedFragment())
                    true
                }
                R.id.Notification -> {
                    replaceFragment(BmiFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(AccountFragment())
                    true
                }
                else -> false
            }
        }
    }



    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.enter_from_right,   // Animation for entering
            R.anim.exit_to_left,       // Animation for exiting
            R.anim.enter_from_left,    // Animation for entering from the other side
            R.anim.exit_to_right       // Animation for exiting to the other side
        )
            .replace(R.id.frameContainer, fragment)
            .commit()
    }

}
