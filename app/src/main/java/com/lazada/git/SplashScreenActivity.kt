package com.lazada.git

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.lazada.git.serp.view.SerpActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_loading)
        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreenActivity, SerpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000L
    }
}
