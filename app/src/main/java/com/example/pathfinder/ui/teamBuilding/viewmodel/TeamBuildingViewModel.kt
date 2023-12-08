package com.example.pathfinder.ui.teamBuilding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pathfinder.data.model.Results
import com.example.pathfinder.data.model.Team
import com.example.pathfinder.domain.usecases.GetTeamDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamBuildingViewModel @Inject constructor(
    private val getTeamDataUseCase: GetTeamDataUseCase
) :
    ViewModel() {

        private val _teamDataList = MutableStateFlow<Results<List<Team>>>(Results.Loading)
        val teamDataList = _teamDataList.asLiveData()

    init {
            getTeamData()
        }

    fun getTeamData() {
            viewModelScope.launch {
                getTeamDataUseCase().collect { result ->
                    when (result) {
                        is Results.Success -> {
                            _teamDataList.value = result
                        }
                        is Results.Loading -> {
                        }
                        is Results.Failure -> {
                        }
                    }
                }
            }
        }

}