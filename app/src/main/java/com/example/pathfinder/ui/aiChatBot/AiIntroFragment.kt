package com.example.pathfinder.ui.aiChatBot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.pathfinder.R
import com.example.pathfinder.databinding.FragmentAiIntroBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AiIntroFragment : Fragment() {

    private lateinit var binding: FragmentAiIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ai_intro, container, false)

        // 로봇 버튼 클릭 리스너 설정
        binding.btnStartChat.setOnClickListener {
            navigateToQuestionnaire()
        }

        // 채팅창 클릭 리스너
        binding.aiInputText.setOnClickListener {
            navigateToQuestionnaire()
        }

        // 뒤로가기
        val fragmentManager: FragmentManager = parentFragmentManager

        binding.buttonBack.setOnClickListener {
            clearBackStack(fragmentManager)
        }

        hideBottomNavigation(true);

        return binding.root
    }

    private fun navigateToQuestionnaire() {

        findNavController().navigate(R.id.action_aiIntroFragment_to_aiQuestionFragment)
    }


    private fun clearBackStack(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        hideBottomNavigation(false)
//    }

    fun hideBottomNavigation(bool: Boolean) {
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (bool)
            bottomNavigation.visibility = View.GONE
        else
            bottomNavigation.visibility = View.VISIBLE
    }
}


