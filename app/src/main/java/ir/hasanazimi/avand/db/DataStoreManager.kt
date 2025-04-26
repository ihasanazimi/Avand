package ir.hasanazimi.avand.db

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// DataStore initialization
val Context.dataStore by preferencesDataStore(name = "AvandDataStoreFile")

class DataStoreManager(private val context: Context) {


    val TAG = "DataStoreManager"

    /** keys */
    companion object {
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val WAKE_TIME = stringPreferencesKey("WAKE_TIME")
        val BED_TIME = stringPreferencesKey("BED_TIME")
        val AUTOMATIC_WEATHER_DATA = stringPreferencesKey("AUTOMATIC_WEATHER_DATA")
        val MANUAL_WEATHER_DATA = stringPreferencesKey("MANUAL_WEATHER_DATA")
        val DEFAULT_CITY = stringPreferencesKey("DEFAULT_CITY")
        val INTRO_SKIPPED = booleanPreferencesKey("INTRO_SKIPPED")
    }


    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name.also {
                Log.i(TAG, "saveUserName: $it")
            }
        }
    }
    val userNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }




    suspend fun saveWakeTime(wakeTime: String) {
        context.dataStore.edit { preferences ->
            preferences[WAKE_TIME] = wakeTime.also {
                Log.i(TAG, "saveWakeTime: $it")
            }
        }
    }
    val wakeTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[WAKE_TIME]
    }





    suspend fun saveBedTime(BedTime: String) {
        context.dataStore.edit { preferences ->
            preferences[BED_TIME] = BedTime.also {
                Log.i(TAG, "saveBedTime: $it")
            }
        }
    }
    val bedTimeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[BED_TIME]
    }




    suspend fun saveAutomaticWeatherData(weatherJson: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTOMATIC_WEATHER_DATA] = weatherJson.also {
                Log.i(TAG, "saveAutomaticWeatherData: $it")
            }
        }
    }
    suspend fun removeAutomaticWeatherData(){
        context.dataStore.edit { preferences ->
            preferences.remove(AUTOMATIC_WEATHER_DATA)
        }
    }
    val automaticWeatherDataFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[AUTOMATIC_WEATHER_DATA]
    }




    suspend fun saveManualWeatherData(weatherJson: String) {
        context.dataStore.edit { preferences ->
            preferences[MANUAL_WEATHER_DATA] = weatherJson.also {
                Log.i(TAG, "saveManualWeatherData: $it")
            }
        }
    }
    suspend fun removeManualWeatherData(){
        context.dataStore.edit { preferences ->
            preferences.remove(MANUAL_WEATHER_DATA)
        }
    }
    val manualWeatherData: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[MANUAL_WEATHER_DATA]
    }




    suspend fun saveDefaultCity(city: String) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_CITY] = city.also {
                Log.i(TAG, "saveDefaultCity: $it")
            }
        }
    }
    suspend fun removeDefaultCity(){
        context.dataStore.edit { preferences ->
            preferences.remove(DEFAULT_CITY)
        }
    }
    val defaultCity: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DEFAULT_CITY]
    }





    suspend fun saveIntroSkipped(skipped: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[INTRO_SKIPPED] = skipped.also {
                Log.i(TAG, "saveIntroSkipped: $it")
            }
        }
    }
    val introSkipped: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[INTRO_SKIPPED] == true
    }


}