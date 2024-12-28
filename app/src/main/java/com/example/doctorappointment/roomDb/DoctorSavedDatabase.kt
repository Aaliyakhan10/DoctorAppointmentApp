package com.example.doctorappointment.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoctorsSaved::class], version = 1, exportSchema = false)
abstract class DoctorSavedDatabase:RoomDatabase() {
    abstract fun SavedDao():SavedDao
    companion object{
        @Volatile
        var Instance:DoctorSavedDatabase?=null
        fun getDatabase(context: Context):DoctorSavedDatabase{
            val tempInstances= Instance
            if(tempInstances!=null) return tempInstances
            synchronized(this){
                val roomDb= Room.databaseBuilder(context,DoctorSavedDatabase::class.java,"SavedDoctors").allowMainThreadQueries().build()
                Instance=roomDb
                return roomDb
            }

        }
    }
}