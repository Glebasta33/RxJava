package com.trusov.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val observable = Observable.just(1,2,3)

    val single = Single.just(1)

    val flowable = Flowable.just(1,2,3)

    val disposable = flowable.subscribe ({
        Log.d(TAG, "new data $it")
    }, {

    })

}