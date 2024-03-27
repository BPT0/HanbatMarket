package com.hanbat.hanbatmarket.signin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface SigninAPIService {
    /**
     * POST 방식 - @Body
     * @Body 사용, data class 객체 전송 - Gson 컨버터가 직렬화 담당
     * 회원가입 요청
     * 전체 URI - https://hanbatmarket.loca.lt/api/members/new
     */
    @POST("api/members/new")
    fun createMember(@Body post: SigninDTO?): Call<SigninDTO?>

    /**
     * 로그인 요청
     * @param 로그인 입력정보
     * @return Call 타입: 비동기 HTTP 요청 -> 서버로부터 예상 응답은 SigninDTO?
     */
    @POST("api/member/login")
    fun loginMember(@Body post: LoginDTO?): Call<LoginDTO?>

 }

