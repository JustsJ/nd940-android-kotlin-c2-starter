package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.getDatabase
import com.udacity.asteroidradar.repository.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application): ViewModel() {

    val database = getDatabase(application)
    val repository = AsteroidRepository(database)

    val asteroids = repository.asteroids

    private val _navigateToDetails = MutableLiveData<Asteroid?>(null)
    val navigateToDetails: LiveData<Asteroid?>
        get() = _navigateToDetails

    init {
        Log.i("MainView","Model init")
        viewModelScope.launch {
            repository.refreshAsteroids()
        }
    }

    fun navigateToDetails(asteroidId: Long){
        viewModelScope.launch {
            _navigateToDetails.value = repository.getAsteroid(asteroidId)
        }
    }

    fun navigateToDetailsComplete(){
        _navigateToDetails.value = null
    }
}