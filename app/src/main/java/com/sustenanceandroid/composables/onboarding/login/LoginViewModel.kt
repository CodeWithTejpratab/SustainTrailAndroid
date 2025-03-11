package com.sustenanceandroid.composables.onboarding.login

import com.sustenanceandroid.mainScreen.BaseViewModel
import com.sustenanceandroid.module.AppModule.firebaseAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : BaseViewModel() {

    data class RegisterViewState(
        val userName: String = "",
        val email: String = "",
        val password: String = "",
        val retypedPassword: String = "",
        val location: String = "",
        val showError: Boolean = false
    )

    data class LoginViewState(
        val userName: String = "",
        val password: String = "",
        val showError: Boolean = false
    )

    private val _loginViewState = MutableStateFlow(LoginViewState())
    val loginViewState = _loginViewState.asStateFlow()

    private val _registerViewState = MutableStateFlow(RegisterViewState())
    val registerViewState = _registerViewState.asStateFlow()

    fun updateLoginUserName(userName: String) {
        _loginViewState.update {
            it.copy(userName = userName, showError = false)
        }
    }

    fun updateLoginPassword(password: String) {
        _loginViewState.update {
            it.copy(password = password, showError = false)
        }
    }

    fun updateRegisterUserName(userName: String) {
        _registerViewState.update {
            it.copy(userName = userName, showError = false)
        }
    }

    fun updateRegisterEmail(email: String) {
        _registerViewState.update {
            it.copy(email = email, showError = false)
        }
    }

    fun updateRegisterPassword(password: String) {
        _registerViewState.update {
            it.copy(password = password, showError = false)
        }
    }

    fun updateRegisterRetypedPassword(password: String) {
        _registerViewState.update {
            it.copy(retypedPassword = password, showError = false)
        }
    }

    fun updateRegisterLocation(location: String) {
        _registerViewState.update {
            it.copy(location = location)
        }
    }

    fun registerUser(didAgreeToTerm: Boolean) {
        if (!didAgreeToTerm && _registerViewState.value.email.isEmpty() && _registerViewState.value.password.isEmpty()) return

        launchCatchingBlock {
            firebaseAuthManager.createAccount(
                _registerViewState.value.email, _registerViewState.value.password
            ) { success ->
                if (!success) _registerViewState.update { it.copy(showError = true) }
            }
        }
    }

    fun loginUser() {
        if (loginViewState.value.userName.isEmpty() && loginViewState.value.password.isEmpty()) return

        launchCatchingBlock {
            firebaseAuthManager.singIn(
                _loginViewState.value.userName, _loginViewState.value.password
            ) { success ->
                if (!success) _loginViewState.update { it.copy(showError = true) }
            }
        }
    }
}
