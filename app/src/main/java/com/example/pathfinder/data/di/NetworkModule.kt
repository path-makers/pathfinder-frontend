package com.example.pathfinder.data.di



import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    private val URL = "http://138.2.114.130:8080/api/board/"


    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    //Gson은 JSON을 자바 객체로 변환하거나 자바 객체를 JSON으로 변환하는데 사용한다.


    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    //레트로핏 빌더 생성
}


//    @Provides
//    @Singleton
//    fun provideNetworkInterceptor(proofPreference: ProofPreference): NetworkInterceptor {
//        return NetworkInterceptor(proofPreference = proofPreference)
//    }
//인터셉트를 할수 있게 해준다.

//    @Provides
//    @Singleton
//    fun provideOkHttp3Client(networkInterceptor: NetworkInterceptor): OkHttpClient {
//        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(
//            networkInterceptor
//        )
//        return okHttpClientBuilder.build()
//    }
//OkHttp는 데이터 요청, 응답시에 인터셉트해 추가처리를 해준다. 현재 프로젝트 상 굳이?