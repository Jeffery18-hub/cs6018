package com.example.lab4

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton constructor(context: Context) {
    companion object {
        @Volatile // this notation is make sure 'instance' is visible to all threads and if one thread changes the instance, all the other threads can see immediately
        private var INSTANCE: VolleySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE?: synchronized(this) {  // '?:' is elvis operator
                INSTANCE?: VolleySingleton(context).also {// instance? is double check to avoid multi threads create multi instances
                    INSTANCE = it
                }
            }
    }

    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}