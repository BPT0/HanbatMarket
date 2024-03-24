package com.hanbat.hanbatmarket.login

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat
import com.hanbat.hanbatmarket.R
import com.hanbat.hanbatmarket.base.BaseActivity
import com.hanbat.hanbatmarket.databinding.ActivitySigninSucessBinding

class SigninSuccessActivity : BaseActivity<ActivitySigninSucessBinding>(){
    override val TAG: String = LoginActivity::class.java.simpleName
    override val layoutRes: Int = R.layout.activity_signin_sucess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Status 및 navigation bar : 투명하게 만들기
        val window = window
        window.statusBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }else{
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        // 가입 완료화면으로 이동
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}