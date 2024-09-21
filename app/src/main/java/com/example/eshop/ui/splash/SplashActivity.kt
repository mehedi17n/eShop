package com.example.eshop.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.eshop.R
import com.example.eshop.ui.auth.LoginActivity
import com.example.eshop.ui.home.MainActivity
import com.example.eshop.ui.home.WelcomeActivity
import com.example.eshop.viewModel.SplashViewModel
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]


        init()

    }

    fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            checkLogin()
        }, 3000)
    }
    fun checkLogin() {
        lifecycleScope.launch {
            viewModel.tokenFlow.collect { token ->

                Log.d("SPLASH",token.toString())
                if (token != null) {
                    Log.d("SPLASH","IF")
                    navigateToHome()
                } else {
                    Log.d("SPLASH","ELSE")
                    navigateToLogin()
                }
            }
        }
    }



    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
