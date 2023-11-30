package com.example.pathfinder

import com.example.pathfinder.data.repository.BoardRefactorRepository
import com.example.pathfinder.domain.model.Results
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlinx.coroutines.flow.first

@HiltAndroidTest
class BoardRefactorRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var boardRefactorRepository: BoardRefactorRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testGetBoardDataByType() = runBlocking {
        // Repository 호출
        val result = boardRefactorRepository.getBoardDataByType("MENTOR").first()

        // 결과 로깅
        println("Test result: $result")

        // 결과 유효성 검증
        when (result) {
            is Results.Success<*> -> {
                assertNotNull("Data should not be null", result.value)
            }
            else -> fail("Result is not a Success")
        }
    }
}
