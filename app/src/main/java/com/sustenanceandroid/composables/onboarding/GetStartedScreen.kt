package com.sustenanceandroid.composables.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.ui.theme.StartingGreen

@Composable
fun GetStartedScreen(onClick: () -> Unit) {
    Box {
        Image(
            painter = painterResource(R.drawable.startingpagebackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize()
        ) {
            ConstraintLayout {
                val (heading, image, container) = createRefs()
                val margin = dimensionResource(R.dimen.starting_page_margin)

                Text(
                    text = stringResource(R.string.starting_page_heading),
                    style = TextStyles.headLine(),
                    modifier = Modifier
                        .alpha(0.88f)
                        .constrainAs(heading) {
                            bottom.linkTo(image.top, margin = margin)
                            centerHorizontallyTo(parent)
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.startpagelogo),
                    contentDescription = stringResource(R.string.starting_page_image_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(image) {
                            top.linkTo(heading.bottom, margin = margin)
                            centerHorizontallyTo(parent)
                        }
                )

                StartingBottomSection(
                    onClick,
                    Modifier.constrainAs(container) {
                        top.linkTo(image.bottom, margin = margin)
                        centerHorizontallyTo(parent)
                    }
                )
            }
        }
    }
}

@Composable
private fun StartingBottomSection(
    onClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.starting_page_margin)),
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.starting_page_message),
            style = TextStyles.normalText1()
        )

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(StartingGreen),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.onboarding_button_height))
        ) {
            Text(
                text = stringResource(R.string.starting_page_button_text),
                style = TextStyles.normalText1().copy(
                    color = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
private fun StartingScreenPreview() {
    GetStartedScreen { }
}
