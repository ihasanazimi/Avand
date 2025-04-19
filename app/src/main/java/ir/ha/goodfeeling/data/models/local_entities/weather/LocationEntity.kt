package ir.ha.goodfeeling.data.models.local_entities.weather

data class LocationEntity(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzId: String,
    val localtimeEpoch: Int,
    val localtime: String,
)