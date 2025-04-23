package com.sustenanceandroid.composables.profile

import com.sustenanceandroid.mainScreen.BaseViewModel
import com.sustenanceandroid.module.AppModule.apiRepository
import com.sustenanceandroid.module.AppModule.firebaseAuthManager

class ProfileViewModel : BaseViewModel() {

    fun deleteAccount() {
        launchCatchingBlock {
            if (apiRepository.deleteUser()) {
                firebaseAuthManager.deleteAccount()
                firebaseAuthManager.signOut()
            }
        }
    }
}
