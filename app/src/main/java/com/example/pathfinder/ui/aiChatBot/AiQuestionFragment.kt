package com.example.pathfinder.ui.aiChatBot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentAiQuestionBinding


import java.util.*
import androidx.fragment.app.FragmentManager
import com.hadi.emojiratingbar.EmojiRatingBar
import com.hadi.emojiratingbar.RateStatus


class AiQuestionFragment : Fragment() {


    private lateinit var binding: FragmentAiQuestionBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAiQuestionBinding.inflate(inflater, container, false)

        binding.btnShowResults.setOnClickListener {
            collectResponsesAndShowResults()
        }

        // 뒤로가기
        val fragmentManager: FragmentManager = parentFragmentManager

        binding.backButton.setOnClickListener {
            clearBackStack(fragmentManager)
        }

        val emojiRatingBar1 = binding.emojiRatingBar1
        val emojiRatingBar2 = binding.emojiRatingBar2
        val emojiRatingBar3 = binding.emojiRatingBar3
        val emojiRatingBar4 = binding.emojiRatingBar4
        val emojiRatingBar5 = binding.emojiRatingBar5
        val emojiRatingBar6 = binding.emojiRatingBar6
        val emojiRatingBar7 = binding.emojiRatingBar7
        val emojiRatingBar8 = binding.emojiRatingBar8
        val emojiRatingBar9 = binding.emojiRatingBar9
        val emojiRatingBar10 = binding.emojiRatingBar10


        setCustomEmojiTitles(emojiRatingBar1)
        setCustomEmojiTitles(emojiRatingBar2)
        setCustomEmojiTitles(emojiRatingBar3)
        setCustomEmojiTitles(emojiRatingBar4)
        setCustomEmojiTitles(emojiRatingBar5)
        setCustomEmojiTitles(emojiRatingBar6)
        setCustomEmojiTitles(emojiRatingBar7)
        setCustomEmojiTitles(emojiRatingBar8)
        setCustomEmojiTitles(emojiRatingBar9)
        setCustomEmojiTitles(emojiRatingBar10)

        return binding.root
    }

    fun setCustomEmojiTitles(emojiRatingBar: EmojiRatingBar) {
        emojiRatingBar.setAwfulEmojiTitle("전혀 없음")
        emojiRatingBar.setBadEmojiTitle("조금 있음")
        emojiRatingBar.setOkayEmojiTitle("보통")
        emojiRatingBar.setGoodEmojiTitle("많음")
        emojiRatingBar.setGreatEmojiTitle("매우 많음")
    }
    fun getRatingValue(rateStatus: RateStatus): Int {
        return when (rateStatus) {
            RateStatus.EMPTY -> 0
            RateStatus.AWFUL -> 1
            RateStatus.BAD -> 2
            RateStatus.OKAY -> 3
            RateStatus.GOOD -> 4
            RateStatus.GREAT -> 5
        }
    }

    private fun collectResponsesAndShowResults() {
        val contentBuilder = StringBuilder()
        val questions = resources.getStringArray(R.array.questions)
        val responses = listOf(
            getRatingValue(binding.emojiRatingBar1.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar2.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar3.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar4.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar5.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar6.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar7.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar8.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar9.getCurrentRateStatus()),
            getRatingValue(binding.emojiRatingBar10.getCurrentRateStatus())
        )


        for (i in responses.indices) {
            contentBuilder.append("${questions[i]}에 대한 점수는 ${responses[i]}.")
        }
        val mbtiType = binding.editTextMBTI.text.toString().trim()
        val wordType = binding.editTextWord.text.toString().trim()
        contentBuilder.append("그리고 제 MBTI는 $mbtiType 이고, ")
        contentBuilder.append("저를 한단어로 표현하면 $wordType 입니다.")

        contentBuilder.append("흥미도를 1부터 5로 평가했어요. 이 정보들을 바탕으로 내게 어울리는 직업을 추천해주세요.")


        val bundle = Bundle()
        bundle.putString("userResponses", contentBuilder.toString())
        Log.d("AiQuestionFragment", "userResponses: ${bundle.getString("userResponses")}")

        findNavController().navigate(R.id.action_aiQuestionFragment_to_chatFragment, bundle)
    }


    private fun clearBackStack(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }



}
