package com.sustenanceandroid.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.sustenanceandroid.ui.theme.LinkColor
import com.sustenanceandroid.ui.theme.OnboardingGreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isReEnterPasswordVisible by remember { mutableStateOf(false) }
    var didAgreetoTerms by remember { mutableStateOf(false) }
    val gutter = dimensionResource(R.dimen.login_gutter)

    Box {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = gutter)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.on_boarding_box_padding))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.welcomemessage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.login_password_margin))
                ) {

                    OutlinedTextField(
                        value = "",
                        placeholder = { Text(stringResource(R.string.login_username)) },
                        onValueChange = { },
                    )

                    OutlinedTextField(
                        value = "",
                        placeholder = { Text(stringResource(R.string.register_page_email)) },
                        onValueChange = { },
                    )

                    OutlinedTextField(
                        value = "",
                        placeholder = { Text(stringResource(R.string.login_password)) },
                        onValueChange = { },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) {
                                        stringResource(R.string.password_content_description_hide)
                                    } else {
                                        stringResource(R.string.password_content_description_show)
                                    }
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = "",
                        placeholder = { Text(stringResource(R.string.register_page_password)) },
                        onValueChange = { },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (isReEnterPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isReEnterPasswordVisible = !isReEnterPasswordVisible }) {
                                Icon(
                                    imageVector = if (isReEnterPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (isReEnterPasswordVisible) {
                                        stringResource(R.string.password_content_description_hide)
                                    } else {
                                        stringResource(R.string.password_content_description_show)
                                    }
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = "",
                        placeholder = { Text(stringResource(R.string.register_page_location)) },
                        onValueChange = { },
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
                            selected = didAgreetoTerms,
                            onClick = { didAgreetoTerms = !didAgreetoTerms }
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
                        onClick = onRegisterClick,
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(gutter),
                    modifier = Modifier.padding(top = gutter)
                ) {
                    Text(
                        text = stringResource(R.string.register_page_account),
                        style = TextStyles.normalText1().copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Text(
                        text = stringResource(R.string.register_page_login),
                        style = TextStyles.normalText1().copy(
                            color = LinkColor,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.clickable { onSignInClick() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onRegisterClick = {}, onSignInClick = {})
}
