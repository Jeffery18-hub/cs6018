package com.example.lab4

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class JokeApplication: Application() {
    val scope = CoroutineScope(SupervisorJob())
    val db by lazy { JokeDB.getDatabase(applicationContext) }
    val jokeRepo by lazy { JokeRepo(applicationContext, scope, db.jokeDao()) }
}