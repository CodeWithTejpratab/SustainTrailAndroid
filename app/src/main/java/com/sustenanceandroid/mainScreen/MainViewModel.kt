package com.sustenanceandroid.mainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.sustenanceandroid.R
import com.sustenanceandroid.injection.AppModule.firebaseAuthManager
import com.sustenanceandroid.injection.AppModule.resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : BaseViewModel() {

    data class BottomNavItem(
        val title: String,
        val selectedItem: ImageVector,
        val unSelectedItem: ImageVector
    )

    val items = listOf(
        BottomNavItem(resources.getString(R.string.home), Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem(resources.getString(R.string.environmental_news), Icons.Filled.Dehaze, Icons.Outlined.Dehaze),
        BottomNavItem(resources.getString(R.string.location), Icons.Filled.LocationOn, Icons.Outlined.LocationOn),
        BottomNavItem(resources.getString(R.string.profile), Icons.Filled.Person, Icons.Outlined.Person)
    )

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    private val _loggedInStatus = MutableStateFlow(firebaseAuthManager.isUserLoggedIn)
    val loggedInStatus = _loggedInStatus.asStateFlow()

    fun onNavigationItemSelected(index: Int) {
        _selectedIndex.value = index
    }

    fun updateLoggedInStatus(b: Boolean) {
        _loggedInStatus.value = b
    }
}
