package ir.ha.goodfeeling.data.models.local_entities.weather

import com.google.gson.annotations.SerializedName

data class ConditionEntity(
    val code: Int,
    val icon: String,
    val text: String
)