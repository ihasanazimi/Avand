package ir.ha.goodfeeling.data.models.remote_response.weather

import com.google.gson.annotations.SerializedName

data class LocationRemoteResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("tz_id")
    val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerializedName("localtime")
    val localtime: String,
)