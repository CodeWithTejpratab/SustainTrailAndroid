package com.sustenanceandroid.composables.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.FileCopy
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.injection.AppModule.firebaseAuthManager
import com.sustenanceandroid.ui.theme.ProfileUsernameDarkBlue

@Composable
fun ProfileScreen() {
    ProfileHeading()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.profile_box_padding))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.profile_content_spacing))
        ) {
            ProfilePicture(
                username = "Jon Snow",
                profilePicture = Icons.Rounded.AccountCircle
            )
            ProfileMenuButtons(
                onClickAbout = { },
                onClickTerms = { },
                onClickEdit = { },
                onClickLogout = { firebaseAuthManager.signOut() }
            )
        }
    }
}

@Composable
fun ProfileHeading() {
    Row(
        modifier = Modifier.padding(
            horizontal = dimensionResource(R.dimen.page_heading_horizontal_spacing),
            vertical = dimensionResource(R.dimen.page_heading_vertical_spacing)
        )
    ) {
        Text(
            text = stringResource(R.string.profile_page_heading),
            style = TextStyles.pageHeading()
        )
    }
}

@Composable
fun ProfilePicture(
    username: String,
    profilePicture: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.profile_content_button_spacing))
    ) {
        Image(
            imageVector = profilePicture,
            contentDescription = stringResource(R.string.profile_picture),
            modifier = Modifier
                .border(BorderStroke(2.dp, Color.Gray), CircleShape)
                .size(dimensionResource(R.dimen.profile_picture_size))
                .clip(CircleShape)
        )

        Text(
            text = username,
            style = TextStyles.profileText().copy(color = ProfileUsernameDarkBlue),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileMenuButtons(
    onClickEdit: () -> Unit,
    onClickAbout: () -> Unit,
    onClickTerms: () -> Unit,
    onClickLogout: () -> Unit
) {
    Column {
        ProfileButton(
            buttonText = stringResource(R.string.profile_button_edit),
            icon = Icons.Rounded.Settings,
            onClick = onClickEdit
        )

        ProfileButton(
            buttonText = stringResource(R.string.profile_button_about),
            icon = Icons.Rounded.Info,
            onClick = onClickAbout
        )

        ProfileButton(
            buttonText = stringResource(R.string.profile_button_terms),
            icon = Icons.Rounded.FileCopy,
            onClick = onClickTerms
        )

        ProfileButton(
            buttonText = stringResource(R.string.profile_button_logout),
            icon = Icons.AutoMirrored.Rounded.Logout,
            textColor = Color.Red,
            hasDivider = false,
            onClick = onClickLogout
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProfileButton(
    buttonText: String,
    icon: ImageVector,
    textColor: Color = Color.Gray,
    hasDivider: Boolean = true,
    onClick: () -> Unit
) {
    Column {
        Row {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = ButtonDefaults.squareShape,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray,
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = buttonText,
                    style = TextStyles.profileText().copy(color = textColor),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = textColor
                )
            }
        }

        if (hasDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                thickness = 1.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}
