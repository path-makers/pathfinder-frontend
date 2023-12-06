package com.example.pathfinder.data.model

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Failure(
        val message: String
    ) : Results<Nothing>()

    object Loading : Results<Nothing>()
}
//이걸 이용해서 Response<Result<Board>> 이런식으로 사용하더라
//이렇게 하면 성공했을때, 실패했을때, 로딩중일때를 구분할 수 있는거같다.
//이걸 이용해서 로딩중일때는 로딩중이라는 화면을 띄우고 하는건데 한번에 처리할수 있게 이렇게 빼두고, 각 데이터 클래스들에 추가구현들을 더 해주는듯
