package com.sustenanceandroid.composables.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sustenanceandroid.R
import com.sustenanceandroid.composables.TextStyles
import com.sustenanceandroid.model.ScoreData
import com.sustenanceandroid.model.ScoreInfo
import com.sustenanceandroid.ui.theme.SustainScoreBlue
import com.sustenanceandroid.ui.theme.SustainScoreGreen
import com.sustenanceandroid.ui.theme.SustainScoreOrange
import com.sustenanceandroid.ui.theme.SustainScorePink
import com.sustenanceandroid.ui.theme.TransparentBrush

@Composable
fun SustainScore(
    scoreData: ScoreData,
    modifier: Modifier = Modifier,
) {
    val colors = listOf(
        SustainScoreBlue,
        SustainScoreOrange,
        SustainScorePink
    )

    Box {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.sustain_score_spacing)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            scoreData.data?.takeIf { it.isNotEmpty() }?.let { chartData ->
                DonutChart(
                    chartData,
                    colors,
                    scoreData.totalValue,
                    modifier
                )
            } ?: run {
                DonutChartError(modifier)
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                scoreData.scoreInfo?.let { scoreInfoList ->
                    itemsIndexed(scoreInfoList) { index, scoreInfo ->
                        ScoreBreakDown(
                            score = scoreInfo.score.orEmpty(),
                            scoreFactor = scoreInfo.scoreFactor.orEmpty(),
                            category = scoreInfo.category.orEmpty(),
                            brush = colors.getOrElse(index) { TransparentBrush }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScoreBreakDown(
    score: String,
    scoreFactor: String,
    category: String,
    brush: Brush
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(
            dimensionResource(R.dimen.sustain_score_break_down_padding)
        )
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    brush = brush,
                    shape = CircleShape
                )
        )

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = score,
                    style = TextStyles.normalText1().copy(
                        fontSize = 14.sp
                    )
                )

                Text(
                    text = scoreFactor,
                    style = TextStyles.normalText1().copy(
                        fontSize = 10.sp,
                        color = SustainScoreGreen
                    ),
                )
            }

            Text(
                text = category,
                style = TextStyles.normalText1().copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                ),
            )
        }
    }
}

@Composable
private fun DonutChart(
    data: List<Float>,
    colors: List<Brush>,
    totalValue: String,
    modifier: Modifier = Modifier
) {
    val total = data.sum()
    val sweepAngles = data.map { it / total * 360f }

    Box(
        modifier = modifier.size(dimensionResource(R.dimen.sustain_score_box_size)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var startAngle = -90f
            val strokeWidth = size.minDimension / 6
            val radius = size.minDimension / 2

            sweepAngles.forEachIndexed { index, angle ->
                drawArc(
                    brush = colors.getOrElse(index) { Brush.linearGradient(listOf(Color.Gray, Color.DarkGray)) },
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(radius * 2 - strokeWidth, radius * 2 - strokeWidth)
                )
                startAngle += angle
            }
        }

        Text(
            text = totalValue,
            style = TextStyles.scoreText()
        )
    }
}

@Composable
private fun DonutChartError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(dimensionResource(R.dimen.sustain_score_box_size)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.sustain_score_chart_error),
            style = TextStyles.normalText1().copy(
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SustainScorePreview() {
    val scoreData = ScoreData().apply {
        data = listOf(300f, 500f, 100f)
        scoreInfo = listOf(
            ScoreInfo().apply {
                score = "300"
                scoreFactor = "(+19)"
                category = "Thing #1"
            },
            ScoreInfo().apply {
                score = "500"
                scoreFactor = "(+22)"
                category = "Thing #2"
            },
            ScoreInfo().apply {
                score = "100"
                scoreFactor = "(+67)"
                category = "Thing #3"
            },
            ScoreInfo().apply {
                score = "160"
                scoreFactor = "(+34)"
                category = "Thing #4"
            },
            ScoreInfo().apply {
                score = "140"
                scoreFactor = "(+87)"
                category = "Thing #5"
            }
        )
    }
    SustainScore(scoreData)
}

@Preview(showBackground = true)
@Composable
private fun SustainScoreErrorPreview() {
    val scoreData = ScoreData().apply {
        data = emptyList()
        scoreInfo = emptyList()
    }
    SustainScore(scoreData)
}
