package com.sustenanceandroid.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.composables.onboarding.login.LoginViewModel
import com.sustenanceandroid.ui.theme.OnboardingGreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RegisterScreen(
    registerViewState: LoginViewModel.RegisterViewState,
    onUserNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRetypedPasswordChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onRegister: (Boolean) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isReEnterPasswordVisible by remember { mutableStateOf(false) }
    var didAgreeToTerms by remember { mutableStateOf(false) }

    val gutter = dimensionResource(R.dimen.login_gutter)

    Box {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.on_boarding_box_padding))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.welcomemessage),
                    contentDescription = stringResource(R.string.welcome_to_sustain_trail),
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.login_password_margin))
                ) {
                    OutlinedTextField(
                        value = registerViewState.userName,
                        placeholder = { Text(stringResource(R.string.login_username)) },
                        isError = registerViewState.showError,
                        onValueChange = onUserNameChange,
                    )

                    OutlinedTextField(
                        value = registerViewState.email,
                        placeholder = { Text(stringResource(R.string.register_page_email)) },
                        isError = registerViewState.showError,
                        onValueChange = onEmailChange,
                    )

                    PasswordField(
                        value = registerViewState.password,
                        placeholder = stringResource(R.string.login_password),
                        isVisible = isPasswordVisible,
                        isError = registerViewState.showError,
                        onVisibilityChange = { isPasswordVisible = it },
                        onValueChange = onPasswordChange
                    )

                    PasswordField(
                        value = registerViewState.retypedPassword,
                        placeholder = stringResource(R.string.register_page_password),
                        isVisible = isReEnterPasswordVisible,
                        isError = registerViewState.showError,
                        onVisibilityChange = { isReEnterPasswordVisible = it },
                        onValueChange = onRetypedPasswordChange
                    )

                    OutlinedTextField(
                        value = registerViewState.location,
                        placeholder = { Text(stringResource(R.string.register_page_location)) },
                        onValueChange = onLocationChange,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(gutter)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(gutter)
                    ) {
                        RadioButton(
                            selected = didAgreeToTerms,
                            onClick = { didAgreeToTerms = !didAgreeToTerms }
                        )

                        Text(
                            text = stringResource(R.string.register_page_terms),
                            style = TextStyles.normalText1().copy(
                                color = Color.Black,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                    Button(
                        onClick = { onRegister(didAgreeToTerms) },
                        colors = ButtonDefaults.buttonColors(OnboardingGreen),
                        shape = ButtonDefaults.squareShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.onboarding_button_height))
                            .padding(horizontal = dimensionResource(R.dimen.login_button_padding))
                    ) {
                        Text(
                            text = stringResource(R.string.login_page_register),
                            style = TextStyles.normalText1().copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    placeholder: String,
    isVisible: Boolean,
    isError: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        placeholder = { Text(placeholder) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = isError,
        trailingIcon = {
            IconButton(onClick = { onVisibilityChange(!isVisible) }) {
                Icon(
                    imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (isVisible) {
                        stringResource(R.string.password_content_description_hide)
                    } else {
                        stringResource(R.string.password_content_description_show)
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        registerViewState = LoginViewModel.RegisterViewState(),
        onUserNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onRetypedPasswordChange = {},
        onLocationChange = {},
        onRegister = {}
    )
}
