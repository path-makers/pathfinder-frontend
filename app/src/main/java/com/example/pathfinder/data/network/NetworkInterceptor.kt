package com.example.pathfinder.data.network


import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//class NetworkInterceptor @Inject constructor(
//    private val proofPreference: ProofPreference
//) : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val accessToken = proofPreference.get("accessToken", "")
//
//        return chain.proceed(
//            request = chain.request().newBuilder().apply {
//                header("accept" , "application/json")
//                header("Authorization", "Bearer $accessToken") // token
//            }
//                .build()
//        )
//    }
//}

// 이 윗부분을 이용해서 okHttp3로 데이터 보내는걸 인터셉트해와 추가 정보를 작성 할수 있는것이다. 말했듯이 현재 프로젝트 상 굳이?
