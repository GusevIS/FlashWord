package com.example.flashword.presentation.statistics

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flashword.R
import com.example.flashword.presentation.statistics.bar_chart.StatisticsBarChart
import com.example.flashword.presentation.statistics.bar_chart.TimePeriod
import com.example.flashword.ui.theme.setStatusBarColor
import kotlin.random.Random


@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    setStatusBarColor(MaterialTheme.colorScheme.background.toArgb())

    StatisticsScreenContent(

    )
}

@Composable
fun StatisticsScreenContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        item {
            StatisticsBarChart(
                stringResource(R.string.reviews_stat),
                stringResource(R.string.number_of_repeated),
                R.string.cards_reviewed,
                R.string.avg_cards_reviewed,
                List(82) { Random.nextInt(0, 80) },
                TimePeriod.MONTH,
                {},
                Color(0xFF4A90E2)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            StatisticsBarChart(
                stringResource(R.string.learned_stat),
                stringResource(R.string.number_of_learned),
                R.string.cards_learned,
                R.string.avg_cards_learned,
                List(84) { Random.nextInt(0, 30) },
                TimePeriod.MONTH,
                {},
                Color(0xFF6BBE67)
            )
        }

    }


}

