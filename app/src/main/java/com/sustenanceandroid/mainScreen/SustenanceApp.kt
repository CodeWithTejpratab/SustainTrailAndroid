package com.sustenanceandroid.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.events.EventScreen
import com.sustenanceandroid.composables.home.HomeScreen
import com.sustenanceandroid.composables.map.MapsScreen
import com.sustenanceandroid.composables.onboarding.login.Nav
import com.sustenanceandroid.composables.onboarding.login.onboardLogin
import com.sustenanceandroid.composables.profile.ProfileScreen
import com.sustenanceandroid.module.AppModule.firebaseAuthManager
import com.sustenanceandroid.module.AppModule.resources
import com.sustenanceandroid.ui.theme.NavBarGreen
import com.sustenanceandroid.ui.theme.SelectedIconColor
import com.sustenanceandroid.ui.theme.SustenanceAndroidTheme
import com.sustenanceandroid.ui.theme.UnselectedIconColor
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun SustenanceApp(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val selectedIndex by viewModel.selectedIndex.collectAsStateWithLifecycle()
    val loggedInStatus by viewModel.loggedInStatus.collectAsStateWithLifecycle()
    val systemUiController = rememberSystemUiController()

    if (loggedInStatus) {
        systemUiController.setNavigationBarColor(Color.Transparent)
    } else {
        systemUiController.setNavigationBarColor(Color.White)
    }

    LaunchedEffect(Unit) {
        firebaseAuthManager.currentUser
            .distinctUntilChanged()
            .collect { user ->
                viewModel.updateLoggedInStatus(user != null)
            }
    }

    SustenanceAndroidTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = if (!loggedInStatus) Nav.LoginFlow.route else Nav.MainApp.route
            ) {
                navigation(
                    startDestination = Nav.GetStarted.route,
                    route = Nav.LoginFlow.route
                ) {
                    onboardLogin(navController)
                }

                composable(Nav.MainApp.route) {
                    MainApp(
                        list = viewModel.items,
                        selectedIndex = selectedIndex,
                        onNavigationItemSelected = viewModel::onNavigationItemSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun MainApp(
    list: List<MainViewModel.BottomNavItem>,
    selectedIndex: Int,
    onNavigationItemSelected: (Int) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                items = list,
                selectedIndex = selectedIndex,
                onNavigationItemSelected = onNavigationItemSelected
            )
        },
    ) { innerPadding ->
        Box {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            NavigationHost(navController, Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun BottomNavBar(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navController: NavController,
    items: List<MainViewModel.BottomNavItem>,
    selectedIndex: Int,
    onNavigationItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = NavBarGreen,
        modifier = Modifier
            .wrapContentHeight()
            .height(110.dp)
    ) {
        items.forEachIndexed { index, item ->

            val isSelected = selectedIndex == index

            NavigationBarItem(
                selected = false,
                onClick = {
                    onNavigationItemSelected(index)
                    navController.navigate(item.title)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .padding(6.dp)
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedItem else item.unSelectedItem,
                            contentDescription = item.title,
                            tint = if (isSelected) SelectedIconColor else UnselectedIconColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(50.dp)
                    .padding(6.dp)
            )
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE && firebaseAuthManager.isUserLoggedIn) {
                onNavigationItemSelected(0)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = stringResource(R.string.home),
        modifier = modifier
    ) {
        composable(resources.getString(R.string.home)) { HomeScreen() }
        composable(resources.getString(R.string.environmental_news)) { EventScreen() }
        composable(resources.getString(R.string.location)) { MapsScreen() }
        composable(resources.getString(R.string.profile)) { ProfileScreen() }
    }
}
