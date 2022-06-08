package com.trusov.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
            button.setOnClickListener {
            Log.e(TAG, "click click")
        }

        val disposable = dataSource()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                button.text = "click-$it"
                Log.e(TAG, "next int $it")
            }, {
                Log.e(TAG, "Throwable: ${it.localizedMessage}")
            })
    }

    private fun dataSource(): Flowable<Int> {
        return Flowable.create ({ subscriber ->
            for (i in 0..500000) {
                subscriber.onNext(i)
            }
            subscriber.onComplete()
        }, BackpressureStrategy.DROP)
    }

}