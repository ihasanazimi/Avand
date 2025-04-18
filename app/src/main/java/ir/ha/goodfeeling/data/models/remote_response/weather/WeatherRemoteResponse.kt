package ir.ha.goodfeeling.data.models.remote_response.weather

import com.google.gson.annotations.SerializedName

data class WeatherRemoteResponse(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: Current
)