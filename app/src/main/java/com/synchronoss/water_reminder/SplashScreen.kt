package com.synchronoss.water_reminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, WalkThroughActivity::class.java)
        startActivity(intent)
            finish()
            },  5000)
    }
}