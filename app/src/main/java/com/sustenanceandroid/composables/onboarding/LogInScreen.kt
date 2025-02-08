package com.sustenanceandroid.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.ui.theme.LinkColor
import com.sustenanceandroid.ui.theme.StartingGreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LogInScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Box {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            ConstraintLayout {
                val (heading, image, username, password, button, row) = createRefs()
                val margin = dimensionResource(R.dimen.starting_page_margin)
                val passmargin = dimensionResource(R.dimen.login_password_margin)
                val buttonpadd = dimensionResource(R.dimen.login_button_padding)
                val rowpadd = dimensionResource(R.dimen.login_row_padding)

                Image(
                    painter = painterResource(R.drawable.welcomemessage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(heading) {
                            bottom.linkTo(image.top)
                            centerHorizontallyTo(parent)
                        }
                        .size(80.dp)
                )

                Image(
                    painter = painterResource(R.drawable.loginimagenobg),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(image) {
                            top.linkTo(heading.bottom)
                            centerHorizontallyTo(parent)
                        }
                )

                OutlinedTextField(
                    value = "Username",
                    onValueChange = {},
                    modifier = Modifier.constrainAs(username) {
                        top.linkTo(image.bottom)
                        centerHorizontallyTo(parent)
                    }
                )

                OutlinedTextField(
                    value = "Password",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.constrainAs(password) {
                        top.linkTo(username.bottom, margin = passmargin)
                        centerHorizontallyTo(parent)
                    }
                )

                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(StartingGreen),
                    shape = ButtonDefaults.squareShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.onboarding_button_height))
                        .padding(horizontal = buttonpadd)
                        .constrainAs(button) {
                            top.linkTo(password.bottom, margin = margin)
                            centerHorizontallyTo(parent)
                        }
                ) {
                    Text(
                        text = stringResource(R.string.login_page_button),
                        style = TextStyles.normalText1().copy(
                            color = Color.White
                        )
                    )
                }

                Row(
                    modifier = Modifier.constrainAs(row) {
                        top.linkTo(button.bottom, margin = rowpadd)
                        centerHorizontallyTo(parent)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.login_page_noacct),
                        style = TextStyles.normalText1().copy(color = Color.Black, fontWeight = FontWeight.Normal)
                    )

                    Spacer(modifier = Modifier.padding(rowpadd))

                    Text(
                        text = stringResource(R.string.login_page_register),
                        style = TextStyles.normalText1().copy(color = LinkColor, fontWeight = FontWeight.Normal),
                        modifier = Modifier.clickable { onRegisterClick() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LogInScreenPreview() {
    LogInScreen(onLoginClick = {}, onRegisterClick = {})
}
