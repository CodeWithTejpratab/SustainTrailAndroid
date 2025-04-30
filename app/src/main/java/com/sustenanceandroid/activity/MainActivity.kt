package com.sustenanceandroid.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.sustenanceandroid.mainScreen.SustenanceApp
import com.sustenanceandroid.module.AppModule.apiRepository
import com.sustenanceandroid.module.AppModule.firebaseAuthManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SustenanceApp()
        }
        sendCheckInEvent()
    }

    private fun sendCheckInEvent() {
        lifecycleScope.launch {
            apiRepository.sendActionEvent("Checkin")
            firebaseAuthManager.updateProfile()
        }
    }
}
