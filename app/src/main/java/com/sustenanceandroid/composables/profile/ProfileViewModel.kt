package com.sustenanceandroid.composables.profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sustenanceandroid.mainScreen.BaseViewModel
import com.sustenanceandroid.model.api.APIRepository
import com.sustenanceandroid.module.AppModule.apiRepository
import com.sustenanceandroid.module.AppModule.firebaseAuthManager

class ProfileViewModel(private val apiRepository: APIRepository) : BaseViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { ProfileViewModel(apiRepository) }
        }
    }

    fun deleteAccount() {
        launchCatchingBlock {
            if (apiRepository.deleteUser()) {
                firebaseAuthManager.deleteAccount()
                firebaseAuthManager.signOut()
            }
        }
    }
}
