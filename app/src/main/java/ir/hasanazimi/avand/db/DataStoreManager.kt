package ir.hasanazimi.avand.db

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// DataStore initialization
val Context.dataStore by preferencesDataStore(name = "MyApplicationSettingsFileName")

class DataStoreManager(private val context: Context) {

    /** keys */
    companion object {
        val USER_NAME = stringPreferencesKey("UserNameKey")
        val WAKE_TIME = stringPreferencesKey("WakeTimeKey")
        val BED_TIME = stringPreferencesKey("BedTimeKey")
        val WEATHER_DATA = stringPreferencesKey("WeatherDataKey")
        val DEFAULT_SELECTED_LOCATION = stringPreferencesKey("SelectedLocationKey")
        val TEAK_LOCATION_BY_GPS = booleanPreferencesKey("TEAK_LOCATION_BY_GPS")
        val INTRO_SKIPPED = booleanPreferencesKey("INTRO_SKIPPED")
    }


    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
    val userNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }




    suspend fun saveWakeTime(wakeTime: String) {
        context.dataStore.edit { preferences ->
            preferences[WAKE_TIME] = wakeTime
        }
    }
    val wakeTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WAKE_TIME]
    }





    suspend fun saveBedTime(BedTime: String) {
        context.dataStore.edit { preferences ->
            preferences[BED_TIME] = BedTime
        }
    }
    val bedTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[BED_TIME]
    }




    suspend fun saveWeatherData(weatherJson: String) {
        context.dataStore.edit { preferences ->
            preferences[WEATHER_DATA] = weatherJson
        }
    }
    val weatherDataFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WEATHER_DATA]
    }




    suspend fun saveSelectedLocation(latitudeLongitude: String, teakLocationByGPS : Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_SELECTED_LOCATION] = latitudeLongitude
            preferences[TEAK_LOCATION_BY_GPS] = teakLocationByGPS
        }
    }
    val selectedLocation: Flow<Pair<String?, Boolean?>> = context.dataStore.data.map { preferences ->
        val city = preferences[DEFAULT_SELECTED_LOCATION]
        val currentlySaved = preferences[TEAK_LOCATION_BY_GPS]
        return@map Pair(city,currentlySaved)
    }


    suspend fun saveIntroSkipped(skipped: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[INTRO_SKIPPED] = skipped
        }
    }
    val introSkipped: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[INTRO_SKIPPED] == true
    }


}