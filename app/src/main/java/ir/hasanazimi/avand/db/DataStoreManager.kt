package ir.hasanazimi.avand.db

import android.content.Context
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
        val SELECTED_LOCATION = stringPreferencesKey("SelectedLocationKey")
    }


    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }
    val userNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }




    suspend fun saveWakeTime(name: String) {
        context.dataStore.edit { preferences ->
            preferences[WAKE_TIME] = name
        }
    }
    val wakeTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WAKE_TIME]
    }





    suspend fun saveBedTime(name: String) {
        context.dataStore.edit { preferences ->
            preferences[BED_TIME] = name
        }
    }
    val bedTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[BED_TIME]
    }




    suspend fun saveWeatherData(name: String) {
        context.dataStore.edit { preferences ->
            preferences[WEATHER_DATA] = name
        }
    }
    val weatherDataFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WEATHER_DATA]
    }




    suspend fun saveSelectedLocation(name: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LOCATION] = name
        }
    }
    val selectedLocationFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_LOCATION]
    }


}