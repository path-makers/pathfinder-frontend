package com.example.pathfinder.data.source.remote.board


import com.example.pathfinder.data.response.model.Board
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BoardApi {
    @GET("/all")
    suspend fun getBoardDataByType(@Query("boardType") boardType: String): Response<List<Board>>
}

// 데이터 소스의 역할을 하는 인터페이스
//이곳에서는 서버와 통신하는 API를 정의한다.
//API를 정의할 때는 suspend 키워드를 붙여준다.
//suspend 키워드는 코루틴을 사용하기 위해 필요한 키워드이다.
//코루틴은 비동기 처리를 위해 사용하는 개념이다.
//todo:코루틴에 대한 자세한 내용은 추후에 더 공부할것
//API를 정의할 때는 @GET, @POST, @PUT, @DELETE 등의 어노테이션을 사용한다.
//이 어노테이션들은 각각 GET, POST, PUT, DELETE 메서드를 의미한다.
//이 어노테이션들은 각각의 메서드에 대한 URL을 지정할 수 있다.
//예를 들어 @GET("/users")와 같이 지정할 수 있다.
//이렇게 지정하면 http://localhost:8080/users와 같은 URL에 대해 GET 메서드를 사용할 수 있다.
//이렇게 api로 작성하는 이유는 추후에 Retrofit을 사용할 때 편리하기 때문이다.
//Retrofit은 이 api를 통해 서버와 통신하게 된다.
//이 api를 통해 서버와 통신하게 되면 서버로부터 받은 데이터를 Response 객체에 담아서 반환하게 된다.
//이때 Response 객체에 담긴 데이터를 가공해서 사용하게 된다.
