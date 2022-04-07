package com.starsolns.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
        name = "DATASTORE_NAME"
    )

    suspend fun saveData(genre: String, genreId: Int, country: String, countryId: Int){
        context.datastore.edit { Pref->
            Pref[selectedGenre] = genre
            Pref[selectedGenreId] = genreId
            Pref[selectedCountry] = country
            Pref[selectedCountryId] = countryId
        }
    }

    val readData : Flow<Movie> = context.datastore.data
        .catch {exception->
            if(exception is IOException){
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map {Pref->
           val selecetedGenre = Pref[selectedGenre] ?: "Action"
            val selectedGenreId = Pref[selectedGenreId] ?: 0
            val selectedCountry = Pref[selectedCountry] ?: "USA"
            val selectedCountryId = Pref[selectedCountryId] ?: 0

            Movie(
                selecetedGenre,
                selectedGenreId,
                selectedCountry,
                selectedCountryId
            )
        }



    data class Movie(
        var genre: String,
        var genreId: Int,
        var country: String,
        var countryId: Int
    )

    companion object {
        val selectedGenre = stringPreferencesKey("genre")
        val selectedGenreId = intPreferencesKey("genreId")
        val selectedCountry = stringPreferencesKey("country")
        val selectedCountryId = intPreferencesKey("countryId")
    }

}