package com.udacity.asteroidradar.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity
data class DatabaseAsteroid(
    @PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)

fun DatabaseAsteroid.toDomainModel(): Asteroid{
    return Asteroid(id,codename,closeApproachDate,
        absoluteMagnitude, estimatedDiameter, relativeVelocity,
        distanceFromEarth, isPotentiallyHazardous)
}

fun List<DatabaseAsteroid>.toDomainModel(): List<Asteroid>{
    return map{
        Asteroid(it.id, it.codename, it.closeApproachDate, it.absoluteMagnitude,
         it.estimatedDiameter, it.relativeVelocity, it.distanceFromEarth, it.isPotentiallyHazardous)
    }

}