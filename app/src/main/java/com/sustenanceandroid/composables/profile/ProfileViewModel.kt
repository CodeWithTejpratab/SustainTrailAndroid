package com.sustenanceandroid.composables.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sustenanceandroid.model.api.UserRepository
import com.sustenanceandroid.module.AppModule.firebaseAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _deleteAccountStatus = MutableStateFlow<DeleteAccountStatus>(DeleteAccountStatus.Idle)
    val deleteAccountStatus: StateFlow<DeleteAccountStatus> = _deleteAccountStatus

    fun deleteAccount() {
        viewModelScope.launch {
            _deleteAccountStatus.value = DeleteAccountStatus.Loading
            try {
                val success = userRepository.deleteUser("123")
                if (success) {
                    firebaseAuthManager.signOut()
                    _deleteAccountStatus.value = DeleteAccountStatus.Success
                } else {
                    _deleteAccountStatus.value = DeleteAccountStatus.Error("Failed to delete account")
                }
            } catch (e: Exception) {
                _deleteAccountStatus.value = DeleteAccountStatus.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

sealed class DeleteAccountStatus {
    object Idle : DeleteAccountStatus()
    object Loading : DeleteAccountStatus()
    object Success : DeleteAccountStatus()
    data class Error(val message: String) : DeleteAccountStatus()
}
