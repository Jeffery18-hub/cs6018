package com.example.lab4

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokeViewModel(private val repo: JokeRepo): ViewModel() {
    // use mutableStateOf to store state，and sync with Compose
    val currJoke: LiveData<JokeData> = repo.currJoke
    val allJokes: LiveData<List<JokeData>> = repo.allJokes

    fun fetchJoke(){
        repo.fetchJokeVolley()
    }
}

// This factory class allows us to define custom constructors for the view model
class JokeViewModelFactory(private val repository: JokeRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JokeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}