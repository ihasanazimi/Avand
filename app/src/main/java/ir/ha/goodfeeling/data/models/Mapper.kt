package ir.ha.goodfeeling.data.models

import ir.ha.goodfeeling.data.models.local_entities.weather.AirQualityEntity
import ir.ha.goodfeeling.data.models.local_entities.weather.ConditionEntity
import ir.ha.goodfeeling.data.models.local_entities.weather.CurrentEntity
import ir.ha.goodfeeling.data.models.local_entities.weather.LocationEntity
import ir.ha.goodfeeling.data.models.local_entities.weather.WeatherEntity
import ir.ha.goodfeeling.data.models.remote_response.weather.AirQualityRemoteResponse
import ir.ha.goodfeeling.data.models.remote_response.weather.ConditionRemoteResponse
import ir.ha.goodfeeling.data.models.remote_response.weather.CurrentRemoteResponse
import ir.ha.goodfeeling.data.models.remote_response.weather.LocationRemoteResponse
import ir.ha.goodfeeling.data.models.remote_response.weather.WeatherRemoteResponse

fun LocationRemoteResponse.toLocationEntity() = LocationEntity(
    name = this.name,
    region = this.region,
    country = this.country,
    lat = this.lat,
    lon = this.lon,
    tzId = this.tzId,
    localtimeEpoch = this.localtimeEpoch,
    localtime = this.localtime
)


fun AirQualityRemoteResponse.toAirQualityEntity() = AirQualityEntity(
    co = this.co,
    gbDefraIndex = this.gbDefraIndex,
    no2 = this.no2,
    o3 = this.o3,
    pm10 = this.pm10,
    pm2_5 = this.pm2_5,
    so2 = this.so2,
    usEpaIndex = this.usEpaIndex
)


fun ConditionRemoteResponse.toConditionEntity() = ConditionEntity(
    code = this.code,
    icon = this.icon,
    text = this.text
)

fun CurrentRemoteResponse.toCurrentEntity() = CurrentEntity(
    airQuality = this.airQuality.toAirQualityEntity(),
    cloud = this.cloud,
    condition = this.condition.toConditionEntity(),
    dewpointC = this.dewpointC,
    dewpointF = this.dewpointF,
    feelslikeC = this.feelslikeC,
    feelslikeF = this.feelslikeF,
    gustKph = this.gustKph,
    gustMph = this.gustMph,
    heatindexC = this.heatindexC,
    heatindexF = this.heatindexF,
    humidity = this.humidity,
    isDay = this.isDay,
    lastUpdated = this.lastUpdated,
    lastUpdatedEpoch = this.lastUpdatedEpoch,
    precipIn = this.precipIn,
    precipMm = this.precipMm,
    pressureIn = this.pressureIn,
    pressureMb = this.pressureMb,
    tempC = this.tempC,
    tempF = this.tempF,
    uv = this.uv,
    visKm = this.visKm,
    visMiles = this.visMiles,
    windDegree = this.windDegree,
    windDir = this.windDir,
    windKph = this.windKph,
    windMph = this.windMph,
    windchillC = this.windchillC,
    windchillF = this.windchillF
)

fun WeatherRemoteResponse.toWeatherEntity() = WeatherEntity(
    location = this.location.toLocationEntity(),
    current = this.current.toCurrentEntity()
)