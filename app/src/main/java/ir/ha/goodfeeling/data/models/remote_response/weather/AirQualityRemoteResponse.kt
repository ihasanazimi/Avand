package ir.ha.goodfeeling.data.models.remote_response.weather

import com.google.gson.annotations.SerializedName

data class AirQualityRemoteResponse(
    @SerializedName("co")
    val co: Double,
    @SerializedName("gb-defra-index")
    val gbDefraIndex: Int,
    @SerializedName("no2")
    val no2: Double,
    @SerializedName("o3")
    val o3: Double,
    @SerializedName("pm10")
    val pm10: Double,
    @SerializedName("pm2_5")
    val pm2_5: Double,
    @SerializedName("so2")
    val so2: Double,
    @SerializedName("us-epa-index")
    val usEpaIndex: Int
)