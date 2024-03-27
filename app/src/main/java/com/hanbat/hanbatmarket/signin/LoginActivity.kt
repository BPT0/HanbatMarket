package com.hanbat.hanbatmarket.signin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsetsController
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.hanbat.hanbatmarket.R
import com.hanbat.hanbatmarket.base.BaseActivity
import com.hanbat.hanbatmarket.databinding.ActivityLoginBinding
import com.hanbat.hanbatmarket.mainpapge.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity() : BaseActivity<ActivityLoginBinding>() {
    override val TAG: String = LoginActivity::class.java.simpleName
    override val layoutRes: Int = R.layout.activity_login

    @SuppressLint("ClickableViewAccessibility")
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

        binding.apply {
            // EditText 외부영역 터치시 키보드 내리고 포커스를 해제
            root.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    hideKeyboard()
                    etxEmail.clearFocus()
                    etxPw.clearFocus()
                }
                false
            }

            etxPw.apply{
                // 엔터버튼 클릭시 로그인 버튼 클릭
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // 로그인 버튼 클릭
                        btnLogin.performClick()
                        return@setOnEditorActionListener true
                    }
                    false
                }


            }

            // 회원가입 화면 이동
            btnLogin.setOnClickListener {
                loginService()
            }

            btnGoSignin.setOnClickListener {
                // Signin 화면으로 이동
                val intent = Intent(this@LoginActivity, SigninActivity::class.java)
                startActivity(intent)
            }

        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.etx_context_menu, menu)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etxEmail.windowToken, 0)
        imm.hideSoftInputFromWindow(binding.etxPw.windowToken, 0)
    }

    /**
     * 로그인 서비스 처리 함수
     */
    private fun loginService() {
        binding.apply {
            val signupService = SigninInstance.signupService
            CoroutineScope(Dispatchers.IO).launch {
                val response = signupService.loginMember(
                    LoginDTO(etxEmail.text.toString(), etxPw.text.toString())
                ).execute()
                Log.d("응답값", response.toString())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        // 성공적으로 응답 받은 경우
                        val token = response.body()!!
                        Log.d(TAG, token.toString())
                        Toast.makeText(this@LoginActivity, token.toString(), Toast.LENGTH_SHORT).show()

                        // main 화면으로 이동
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // 서버에서 오류가 발생한 경우
                        Toast.makeText(this@LoginActivity, "서버에러", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

}
