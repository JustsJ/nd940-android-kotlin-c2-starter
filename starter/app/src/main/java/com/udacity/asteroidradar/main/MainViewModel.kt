package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.repository.getDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): ViewModel() {

    val database = getDatabase(application)
    val repository = AsteroidRepository(database)

    val asteroids = repository.asteroids

    private val _navigateToDetails = MutableLiveData<Asteroid?>(null)
    val navigateToDetails: LiveData<Asteroid?>
        get() = _navigateToDetails

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfTheDay

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            _pictureOfTheDay.value = repository.getPictureOfTheDay()
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