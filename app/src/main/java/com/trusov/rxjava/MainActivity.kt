package com.trusov.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTag"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Log.e(TAG, "click click")
        }

        val disposable: Disposable = Observable.create<Int> { subscriber ->
            listOf(1, 2, 3, 4, 5).forEach {
                Thread.sleep(1000)
                subscriber.onNext(it)
            }
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "value is $it")
            }

        Handler(Looper.getMainLooper()).postDelayed({
            disposable.dispose()
            Log.d(TAG, "Disposed")
        }, 3000)
    }


}