package com.example.flashword.presentation.studying_cards

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashword.R
import com.example.flashword.domain.usecases.RecallQuality
import com.example.flashword.ui.theme.md_theme_light_success
import com.example.flashword.ui.theme.setStatusBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyingCardsScreen(
    state: StudyingCardsState,
    onRecallClick: (RecallQuality) -> Unit,
    onShowAnswerClick: () -> Unit,
    onPopBackClick: () -> Unit,
) {
    setStatusBarColor(MaterialTheme.colorScheme.surface.toArgb())

    Column(
        Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        CenterAlignedTopAppBar (
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            ),
            title = {
                Text(
                    text = state.deckTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

            },
            navigationIcon = {
                IconButton(onClick = onPopBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        )

        DeckProgressBar(state.progress)

        StudyingCardsScreenContent(
            frontText = state.card.frontText,
            backText = state.card.backText,
            isBackSide = state.isBackSide,
            reviewingEnded = state.reviewingEnded,
            cardIndex = state.cardIndex,
            cardsReviewed = state.totalCards,
            onRecallClick = onRecallClick,
            onShowAnswerClick = onShowAnswerClick
        )
    }

}

@Composable
fun StudyingCardsScreenContent(
    frontText: String,
    backText: String,
    isBackSide: Boolean,
    reviewingEnded: Boolean,
    cardIndex: Int = 0,
    cardsReviewed: Int = 0,
    onRecallClick: ( RecallQuality) -> Unit,
    onShowAnswerClick: () -> Unit,

) {
    LazyColumn(Modifier.background(MaterialTheme.colorScheme.surface)) {
        item {
            if (reviewingEnded) {
                val density = LocalDensity.current
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) { isVisible = true }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically {
                        with(density) { -90.dp.roundToPx() }
                    } + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                    ) {
                        StudyingCard() {
                            StudyingCompletedCard(cardsReviewed)
                        }
                    }
                }
            } else {
                AnimatedContent(
                    cardIndex, //TODO change to any other key
                    transitionSpec = {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    },
                    label = "animated content"
                ) { index ->
                    Log.d("screen", index.toString())
                    val animatedRotation by animateFloatAsState(
                        targetValue = if (isBackSide) 180f else 0f,
                        animationSpec = tween(durationMillis = 1200),
                        label = "flip_animation"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                rotationY = animatedRotation
                                cameraDistance = 56 * density
                            }
                            .padding(bottom = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (animatedRotation <= 90f) {
                            StudyingCard() {
                                StudyingFrontCard(frontText, onShowAnswerClick)
                            }
                        } else {
                            StudyingCard(
                                modifier = Modifier.graphicsLayer { scaleX = -1f },
                            ) {
                                StudyingBackCard(backText, onRecallClick)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudyingCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
    ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(content = content)
    }
}

@Composable
fun StudyingBackCard(
    text: String,
    onClick: (RecallQuality) -> Unit,
    minHeight: Int = 288,
) {
    val options = listOf(RecallQuality.WRONG to stringResource(R.string.wrong),
        RecallQuality.HARD to stringResource(R.string.hard),
        RecallQuality.EASY to stringResource(R.string.easy))

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .heightIn(min = minHeight.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            text = text,
            style = TextStyle(fontSize = 22.sp)
        )
        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                val color = when (label.first) {
                    RecallQuality.WRONG -> MaterialTheme.colorScheme.error
                    RecallQuality.HARD -> MaterialTheme.colorScheme.secondary
                    RecallQuality.EASY -> md_theme_light_success

                }
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size,
                    ),
                    onClick = { onClick(label.first) },
                    selected = false,
                    label = { Text(label.second, color = MaterialTheme.colorScheme.onBackground) },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = color.copy(alpha = 0.9f),
                        inactiveContainerColor = color.copy(alpha = 0.9f)
                    )
                )
            }
        }
    }
}


@Composable
fun StudyingFrontCard(
    text: String,
    onShowAnswerClick: () -> Unit,
    minHeight: Int = 288,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .heightIn(min = minHeight.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            text = text,
            style = TextStyle(fontSize = 22.sp)
        )

        ElevatedButton(
            onClick = onShowAnswerClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_360_24),
                contentDescription = "rotate",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun StudyingCompletedCard(
    cardsReviewed: Int = 0,
    minHeight: Int = 288,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .height(minHeight.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicText(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.reviewing_ended),
            style = TextStyle(fontSize = 26.sp,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        )
        BasicText(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.well_done),
            style = TextStyle(fontSize = 26.sp,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        )
        BasicText(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.you_reviewed, cardsReviewed),
            style = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Composable
fun DeckProgressBar(
    currentProgress: Float,
) {
    LinearProgressIndicator(
        progress = { currentProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp,),
        trackColor = Color.White,
        color = MaterialTheme.colorScheme.error,
        gapSize = (-15).dp,
        drawStopIndicator = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DeckProgressBarPreview() {
    DeckProgressBar(
       0.8f
    )

}