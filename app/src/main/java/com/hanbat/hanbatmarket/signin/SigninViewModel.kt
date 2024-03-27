package com.hanbat.hanbatmarket.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SigninViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val passwd = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()

    val signinResult = MutableLiveData<String>()

    fun onSigninClicked() {
        val emailValue = email.value ?: ""
        val passwordValue = passwd.value ?: ""
        val nicknameValue = nickname.value ?: ""


        if (isValidEmail(emailValue) && isValidPassword(passwordValue)) {
            viewModelScope.launch {
                try {
                    val response = SigninInstance.signupService.createMember(
                        SigninDTO(emailValue, passwordValue, nicknameValue)
                    ).execute()
                    if (response.isSuccessful) {
                        // 로그인 성공
                        signinResult.postValue("로그인 성공")
                        // 토큰을 저장하고 다음 화면으로 이동
                    } else {
                        // 로그인 실패
                        signinResult.postValue("로그인 실패")
                    }
                } catch (e: Exception) {
                    signinResult.postValue("네트워크 오류")
                }
            }
        } else {
            signinResult.postValue("이메일 또는 비밀번호가 잘못되었습니다.")
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}