package com.example.countdown.presentation.screens.settings_screen.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.use_cases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsAccountViewModel @Inject constructor(
    private val userUseCase: UserUseCase,

): ViewModel() {

    private val _userState = mutableStateOf(UserModel(name = "Default User", wake_up = Date(), go_down = Date()))
    val userState: State<UserModel> = _userState

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        userUseCase.getUser().collectLatest {
            _userState.value = it
        }
    }

    fun updateUser(userModel: UserModel) = viewModelScope.launch {
        userUseCase.insertUser(user = userModel)
    }
}