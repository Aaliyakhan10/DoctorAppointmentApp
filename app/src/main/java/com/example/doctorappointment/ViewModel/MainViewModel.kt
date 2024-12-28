package com.example.doctorappointment.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.doctorappointment.Model.DoctorsModel
import com.example.doctorappointment.Repository.MainRepository
import com.example.doctorappointment.roomDb.DoctorSavedDatabase
import com.example.doctorappointment.roomDb.SavedDao
import com.example.doctorappointment.roomDb.DoctorsSaved

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val repository=MainRepository()
    val savedDoa:SavedDao=DoctorSavedDatabase.getDatabase(application).SavedDao()

    fun loadDoctors(): LiveData<MutableList<DoctorsModel>> {
        return repository.load()

    }
    fun savedDoctors(doctors:DoctorsSaved){
        savedDoa.savedDoctors(doctors)
    }
    suspend fun deletesaved(Id:Int){
        savedDoa.deletesaved(Id)
    }
     fun getSavedDoctor():LiveData<List<DoctorsSaved>>{
        return savedDoa.getSavedDoctors()
    }
    suspend fun getSavedDoctorById(id: Int): DoctorsSaved? {
        return savedDoa.getDoctorById(id)
    }


}