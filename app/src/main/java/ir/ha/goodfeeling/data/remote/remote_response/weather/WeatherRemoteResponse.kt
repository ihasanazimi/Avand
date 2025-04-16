package ir.ha.goodfeeling.data.remote.remote_response.weather

import com.google.gson.annotations.SerializedName

data class WeatherRemoteResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)