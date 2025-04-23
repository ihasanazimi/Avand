package ir.hasanazimi.avand.data.models.remote_response.weather

import com.google.gson.annotations.SerializedName

data class ConditionRemoteResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)