package ir.hasanazimi.avand.data.models.local_entities.weather

data class WeatherEntity(
    val location: LocationEntity,
    val current: CurrentEntity
)