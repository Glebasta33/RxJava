package com.trusov.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.trusov.rxjava.data.ApiFactory
import com.trusov.rxjava.data.TaskDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTag"

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = ApiFactory.apiService.getListOfTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Observable.fromIterable(it) }
            .filter { !it.completed }
            .filter { it.id % 2 == 0 }
            .map {
                TaskDto(
                    completed = it.completed,
                    id = it.id,
                    title = it.title.uppercase(),
                    userId = it.userId
                )
            }
            .subscribe({ task ->
                Log.d(TAG, task.toString())
            }, {
                Log.d(TAG, "onError: ${it.localizedMessage}")
            }, {
                Log.d(TAG, "onComplete")
            })

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Log.e(TAG, "click click")
        }

//        val editText = findViewById<EditText>(R.id.et_test)
//        val dataSource = Observable.create<CharSequence> { subscriber ->
//            editText.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                }
//                override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    subscriber.onNext(char)
//                }
//                override fun afterTextChanged(p0: Editable?) {
//                }
//            })
//        }
//
//        val disposable: Disposable = dataSource
//            .debounce(500, TimeUnit.MILLISECONDS)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                Toast.makeText(application, "Search: $it", Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "$it") // find
//            }
//
//        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}