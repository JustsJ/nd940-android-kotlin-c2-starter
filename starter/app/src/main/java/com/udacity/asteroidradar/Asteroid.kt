package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.repository.DatabaseAsteroid
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable


fun List<Asteroid>.toDatabaseModel(): List<DatabaseAsteroid>{
    return map{
        DatabaseAsteroid(it.id, it.codename, it.closeApproachDate, it.absoluteMagnitude,
        it.estimatedDiameter, it.relativeVelocity, it.distanceFromEarth, it.isPotentiallyHazardous)
    }
}