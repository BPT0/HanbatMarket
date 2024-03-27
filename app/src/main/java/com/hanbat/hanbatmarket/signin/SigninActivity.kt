package com.hanbat.hanbatmarket.signin

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.hanbat.hanbatmarket.R
import com.hanbat.hanbatmarket.base.BaseActivity
import com.hanbat.hanbatmarket.databinding.ActivitySigninBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SigninActivity : BaseActivity<ActivitySigninBinding>() {
    override val TAG: String = SigninActivity::class.java.simpleName
    override val layoutRes: Int = R.layout.activity_signin

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
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        // 가입 완료화면으로 이동
        binding.btnSignin.setOnClickListener {
            signInService()
        }
    }

    private fun signInService() {
        binding.apply {
            val signupService = SigninInstance.signupService
            CoroutineScope(Dispatchers.IO).launch {
                val response = signupService.createMember(
                    SigninDTO(etxEmail.text.toString(), etxPw.text.toString(), etxNick.text.toString())
                ).execute()
                Log.d("응답값", response.toString())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        // 성공적으로 응답 받은 경우
                        val token = response.body()!!
                        Log.d(TAG, token.toString())
                        Toast.makeText(this@SigninActivity, token.toString(), Toast.LENGTH_SHORT).show()
                        // 다음 화면으로 이동
                        val intent = Intent(this@SigninActivity,
                            SigninSuccessActivity::class.java)
                        startActivity(intent)
                        // 토큰을 사용하여 다음 작업 수행
                    } else {
                        // 서버에서 오류가 발생한 경우
                        Toast.makeText(this@SigninActivity, "서버에러", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}