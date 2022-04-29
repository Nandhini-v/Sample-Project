package com.mst.prototype.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mst.prototype.R
import com.mst.prototype.constant.CoreVariables.SPLASH_DELAY
import com.mst.prototype.ui.component.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val nextScreenIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}