package ir.hasanazimi.avand.data.entities.remote.weather

import com.google.gson.annotations.SerializedName

data class WeatherRemoteResponse(
    @SerializedName("location")
    val location: LocationRemoteResponse,
    @SerializedName("current")
    val current: CurrentRemoteResponse
)