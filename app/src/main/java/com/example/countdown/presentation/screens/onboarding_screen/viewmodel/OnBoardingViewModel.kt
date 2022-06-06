package com.example.countdown.presentation.screens.onboarding_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.use_cases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userUseCase: UserUseCase
): ViewModel() {

    private val _state = mutableStateOf<UserModel?>(null)
    val state: State<UserModel?> = _state


    fun insertUser(user: UserModel) = viewModelScope.launch {
        userUseCase.insertUser(user = user)
    }

    fun deleteUser(user: UserModel) = viewModelScope.launch {
        userUseCase.deleteUser(user = user)
    }

    fun updateUser(user: UserModel) = viewModelScope.launch {
        userUseCase.updateUser(user = user)
    }

    private fun getUserByName(username: String) = viewModelScope.launch {
        userUseCase.getUserByUsername(name = username).collectLatest {
            _state.value = it

        }
    }


}