package com.example.flashword.presentation.statistics.bar_chart

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashword.R
import com.example.flashword.ui.theme.FlashWordTheme
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun StatisticsBarChart(
    title: String,
    description: String,
    totalDescription: Int,
    avgDescription: Int,
    chartData: List<Int> = List(82) { Random.nextInt(0, 80) },
    timePeriod: TimePeriod = TimePeriod.MONTH,
    onOptionSelected: (TimePeriod) -> Unit = {},
    barColor: Color = Color(0xFF6BBE67)
) {
    val reviewBarChartState = chartData.toReviewBarChartState(timePeriod)

    Surface(
        shadowElevation = 12.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        val radioOptions = TimePeriod.entries.toTypedArray()

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(8.dp)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .selectableGroup()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                radioOptions.forEach { timePeriod ->
                    Column(
                        Modifier
                            .selectable(
                                selected = (timePeriod == reviewBarChartState.timePeriod),
                                onClick = { onOptionSelected(timePeriod) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            selected = (timePeriod == reviewBarChartState.timePeriod),
                            onClick = null, // null recommended for accessibility with screen readers
                            colors = RadioButtonColors(
                                selectedColor = MaterialTheme.colorScheme.onBackground,
                                unselectedColor = MaterialTheme.colorScheme.onBackground,
                                disabledSelectedColor = MaterialTheme.colorScheme.onBackground,
                                disabledUnselectedColor = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        val text = when (timePeriod) {
                            TimePeriod.MONTH -> stringResource(R.string.month_stat)
                            TimePeriod.QUARTER -> stringResource(R.string.quarter_stat)
                            TimePeriod.YEAR -> stringResource(R.string.year_stat)
                        }

                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            //modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Chart(
                reviewBarChartState.barData,
                reviewBarChartState.labelCoefficient,
                barColor
            )

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(totalDescription, reviewBarChartState.barData.sum().toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = stringResource(avgDescription, reviewBarChartState.avgCardsPerDay.roundToInt().toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun Chart(
    barData: List<Int>,
    labelCoefficient: Int = 1,
    barColor: Color = Color(0xFF6BBE67)
) {
    val barNumber = barData.size

    ColumnChart(
        gridProperties = GridProperties(true, xAxisProperties = GridProperties.AxisProperties()),
        modifier = Modifier.height(280.dp),
        data = remember {
            List(barNumber) {
                val label = (-barNumber + it + 1) * labelCoefficient
                Bars(
                    label = if ((label % 5 == 0) or (label == 0)) label.toString() else " ",
                    values = listOf(
                        Bars.Data(
                            value = barData[it].toDouble(),
                            color = Brush.linearGradient(
                                listOf(barColor, barColor)
                            )
                        )
                    )
                )
            }
        },
        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = 1.dp,
            thickness = 5.dp,
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        labelHelperProperties = LabelHelperProperties(enabled = false),
        
    )
}

data class ReviewBarChartState(
    val timePeriod: TimePeriod = TimePeriod.QUARTER,
    var labelCoefficient: Int = 1,
    var avgCardsPerDay: Double = 0.0,
    val barData: List<Int> = List(82) { Random.nextInt(0, 80) },
)

fun List<Int>.toReviewBarChartState(timePeriod: TimePeriod = TimePeriod.MONTH): ReviewBarChartState {
    var labelCoefficient = 1
    var avgCardsPerDay = 0.0

    val barData =
        when (timePeriod) {
            TimePeriod.MONTH -> {
                val paddedCardsPerDay = List(maxOf(0, 31 - this.size)) { 0 } + this
                paddedCardsPerDay.takeLast(31).also { avgCardsPerDay = it.average() }
            }
            TimePeriod.QUARTER -> {
                labelCoefficient = 3
                var paddedCardsPerDay = List(maxOf(0, 91 - this.size)) { 0 } + this
                paddedCardsPerDay = paddedCardsPerDay.takeLast(91)             //data is grouped by 3 days
                avgCardsPerDay = paddedCardsPerDay.average()
                paddedCardsPerDay
                    .chunked(3)
                    .map { threeDays -> threeDays.sum() }
            }
            TimePeriod.YEAR -> {
                labelCoefficient = 7
                var paddedCardsPerDay = List(maxOf(0, 366 - this.size)) { 0 } + this
                paddedCardsPerDay = paddedCardsPerDay.takeLast(366)             //data is grouped by weeks
                avgCardsPerDay = paddedCardsPerDay.average()
                paddedCardsPerDay
                    .chunked(7)
                    .map { threeDays -> threeDays.sum() }
            }
        }

    return ReviewBarChartState(timePeriod, labelCoefficient, avgCardsPerDay, barData)
}

enum class TimePeriod {
    MONTH,
    QUARTER,
    YEAR
}

//@Preview(showBackground = true)
//@Composable
//private fun StatisticsBarChartPreview() {
//    FlashWordTheme {
//        StatisticsBarChart(stringResource(R.string.reviews_stat),
//            stringResource(R.string.number_of_repeated))
//    }
//}