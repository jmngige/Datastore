package com.starsolns.datastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.starsolns.datastore.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) : AndroidViewModel(application) {

    val readDatastore = dataStoreRepository.readData

    fun saveData(genre: String, genreId: Int, country: String, countryId: Int){
        viewModelScope.launch {
            dataStoreRepository.saveData(genre, genreId, country, countryId)
        }
    }

}