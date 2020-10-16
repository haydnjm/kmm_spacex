package com.example.kmm_spacex.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

fun greet(): String {
    return "hi";
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
