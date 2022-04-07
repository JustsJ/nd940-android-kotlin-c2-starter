package com.udacity.asteroidradar.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.getTodaysFormattedDate
import com.udacity.asteroidradar.api.getTodaysFormattedEndDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidRepository(val database: AsteroidDatabase) {

    val asteroids = Transformations.map(database.asteroidDao.getAllAsteroids()){
        it.toDomainModel()
    }

    suspend fun getPictureOfTheDay(): PictureOfDay?{
        return withContext(Dispatchers.IO){
            try{
                AsteroidApi.service.getPictureOfTheDay()
            }
            catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun getAsteroid(id: Long): Asteroid {
        return withContext(Dispatchers.IO){
           database.asteroidDao.getAsteroid(id).toDomainModel()
        }
    }

    suspend fun refreshAsteroids(){
        getAsteroids(getTodaysFormattedDate(), getTodaysFormattedEndDate())
    }

    private suspend fun getAsteroids(startDate: String, endDate: String){
        withContext(Dispatchers.IO){
            try{
                val asteroidsString = AsteroidApi.service.getAsteroids(startDate, endDate)

                Log.i("net","got asteroids, "+asteroidsString)

                val asteroidJson = JSONObject(asteroidsString)

                val asteroids = parseAsteroidsJsonResult(asteroidJson)

                database.asteroidDao.insertAll(asteroids.toDatabaseModel())
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}