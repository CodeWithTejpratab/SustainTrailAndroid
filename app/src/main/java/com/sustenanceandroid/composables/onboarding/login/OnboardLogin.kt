package com.sustenanceandroid.composables.onboarding.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sustenanceandroid.composables.onboarding.GetStartedScreen
import com.sustenanceandroid.composables.onboarding.LogInScreen
import com.sustenanceandroid.composables.onboarding.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Nav(val route: String) {
    @Serializable data object GetStarted : Nav("get_started")
    @Serializable data object Login : Nav("login")
    @Serializable data object Register : Nav("register")
    @Serializable data object MainApp : Nav("MAIN_APP")
    @Serializable data object LoginFlow : Nav("LOGIN_FLOW")
}

fun NavGraphBuilder.onboardLogin(navController: NavHostController) {
    composable(Nav.GetStarted.route) {
        GetStartedScreen {
            navController.navigate(Nav.Login.route)
        }
    }

    composable(Nav.Login.route) {
        val viewModel = it.sharedViewModel<LoginViewModel>(navController)
        val loginViewState by viewModel.loginViewState.collectAsStateWithLifecycle()
        LogInScreen(
            loginViewState = loginViewState,
            onUserNameChange = viewModel::updateLoginUserName,
            onPasswordChange = viewModel::updateLoginPassword,
            onLogin = {
                viewModel.loginUser()
                navController.navigate(Nav.MainApp.route) {
                    popUpTo(Nav.LoginFlow.route) {
                        inclusive = true
                    }
                }
            },
            onRegisterClick = {
                navController.navigate(Nav.Register.route)
            }
        )
    }

    composable(Nav.Register.route) {
        val viewModel = it.sharedViewModel<LoginViewModel>(navController)
        val registerViewState by viewModel.registerViewState.collectAsStateWithLifecycle()
        RegisterScreen(
            registerViewState = registerViewState,
            onUserNameChange = viewModel::updateRegisterUserName,
            onEmailChange = viewModel::updateRegisterEmail,
            onPasswordChange = viewModel::updateRegisterPassword,
            onRetypedPasswordChange = viewModel::updateRegisterRetypedPassword,
            onLocationChange = viewModel::updateRegisterLocation,
            onRegister = viewModel::registerUser
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
