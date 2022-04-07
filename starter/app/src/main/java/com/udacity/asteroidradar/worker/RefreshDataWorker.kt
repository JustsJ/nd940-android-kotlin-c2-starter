package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.DatabaseAsteroid
import com.udacity.asteroidradar.repository.getDatabase
import java.lang.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository = AsteroidRepository(getDatabase(applicationContext))
        return try{
            repository.refreshAsteroids()
            Result.success()
        }
        catch (e: Exception){
            Result.retry()
        }
    }
}