package com.sustenanceandroid.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.onboarding.login.Nav
import com.sustenanceandroid.composables.onboarding.login.onboardLogin
import com.sustenanceandroid.injection.AppModule.firebaseManager
import com.sustenanceandroid.injection.AppModule.resources
import com.sustenanceandroid.ui.theme.NavBarGreen
import com.sustenanceandroid.ui.theme.SelectedIconColor
import com.sustenanceandroid.ui.theme.SustenanceAndroidTheme
import com.sustenanceandroid.ui.theme.UnselectedIconColor

@Composable
fun SustenanceApp() {
    val viewModel: MainViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    SustenanceAndroidTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            NavHost(
                navController = navController,
                startDestination = if (!firebaseManager.isUserLoggedIn) Nav.LoginFlow.route else Nav.MainApp.route
            ) {
                navigation(
                    startDestination = Nav.GetStarted.route,
                    route = Nav.LoginFlow.route
                ) {
                    onboardLogin(navController)
                }

                composable(Nav.MainApp.route) {
                    MainApp(navController, viewModel)
                }
            }
        }
    }
}

@Composable
private fun MainApp(navController: NavHostController, viewModel: MainViewModel) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(mainNavController, viewModel) },
    ) { innerPadding ->
        NavigationHost(navController, mainNavController, Modifier.padding(innerPadding))
    }
}

@Composable
private fun BottomNavBar(navController: NavController, viewModel: MainViewModel) {

    val selectedIndex by viewModel.selectedIndex.collectAsState()

    NavigationBar(containerColor = NavBarGreen) {
        viewModel.items.forEachIndexed { index, item ->

            val isSelected by remember {
                derivedStateOf { selectedIndex == index }
            }

            NavigationBarItem(
                selected = false,
                onClick = {
                    viewModel.onNavigationItemSelected(index)
                    navController.navigate(item.title)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .padding(6.dp)
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedItem else item.unSelectedItem,
                            contentDescription = item.title,
                            tint = if (isSelected) SelectedIconColor else UnselectedIconColor
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun NavigationHost(
    outerNavController: NavHostController,
    innerNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = innerNavController,
        startDestination = stringResource(R.string.home),
        modifier = modifier
    ) {
        composable(resources.getString(R.string.home)) { }
        composable(resources.getString(R.string.environmental_news)) { }
        composable(resources.getString(R.string.location)) { }
        composable(resources.getString(R.string.profile)) { }
    }
}
