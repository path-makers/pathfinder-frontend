package com.example.pathfinder.data.di

import android.content.SharedPreferences

//interface ProofPreference {
//    val preference: SharedPreferences
//    fun <T : Any> apply(key: String, value: T?)
//    fun <T : Any> commit(key: String, value: T?)
//    fun <T : Any> get(key: String, defaultValue: T): T
//
//
//}

//이 인터페이스는 SharedPreferences와 연관되어 있으며, 애플리케이션의 키-값 기반의 설정 데이터를 관리하는 역할을 한다.
//예를 들어, 사용자의 로그인 상태, 설정, 토큰 등을 저장하고 관리하는 데 사용된다.
//이 인터페이스는 데이터를 저장하고 검색하는 데 사용되는 메소드들을 정의한다. 이를 통해 애플리케이션 전반에 걸쳐 일관된 방식으로 설정 데이터에 접근할 수 있다.

//NetworkInterceptor의 val accessToken = proofPreference.get("accessToken", "")부분에 쓰인다.
//즉 현재는 사용 할필요 없다