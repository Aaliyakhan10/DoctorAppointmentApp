package com.example.doctorappointment.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savedDoctors(doctor:DoctorsSaved)
    @Query("DELETE FROM SavedDoctors WHERE ID=:Id")
    suspend fun deletesaved(Id:Int)
    @Query("SELECT * FROM SavedDoctors")
    fun getSavedDoctors():LiveData<List<DoctorsSaved>>
    // Query to fetch a doctor by their ID
    @Query("SELECT * FROM SavedDoctors WHERE Id = :id LIMIT 1")
    suspend fun getDoctorById(id: Int): DoctorsSaved?
}