package com.example.flashword.presentation.studying_cards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashword.R
import com.example.flashword.domain.model.CardModel
import com.example.flashword.domain.usecases.RecallQuality
import com.example.flashword.presentation.addcard.ExpandingBasicTextField
import com.example.flashword.ui.theme.FlashWordTheme
import com.example.flashword.ui.theme.md_theme_light_success
import com.example.flashword.ui.theme.setStatusBarColor

@Composable
fun StudyingCardsScreen(
    state: StudyingCardsState,
    onClick: (RecallQuality) -> Unit,

) {
    setStatusBarColor(MaterialTheme.colorScheme.background.toArgb())

    val text: String = if (state.cards.isEmpty()) "" else {
        if (state.isBackSide) state.cards.first().backText
        else state.cards.first().frontText
    }
    StudyingCardsScreenContent(
        text = text,
        onClick = onClick
    )



}

@Composable
fun StudyingCardsScreenContent(
    text: String,

    onClick: ( RecallQuality) -> Unit,

) {
    LazyColumn {

        item {

//            val animatedRotation by animateFloatAsState(
//                targetValue = if (isBackSide) 180f else 0f,
//                animationSpec = tween(durationMillis = 1200),
//                label = "flip_animation"
//            )

            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .graphicsLayer {
//                        rotationY = animatedRotation
//                        cameraDistance = 24 * density
//                    },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    description = stringResource(R.string.enter_front_desc),
                    text = text,
                    onClick = onClick //updateFrontText
                )
//                if (animatedRotation <= 90f) {
//                    Card(
//                        description = stringResource(R.string.enter_front_desc),
//                        text = "test front text",
//                        onTextChanged = updateFrontText
//                    )
//                } else {
//                    Card(
//                        description = stringResource(R.string.enter_back_desc),
//                        modifier = Modifier.graphicsLayer { scaleX = -1f },
//                        text = backText,
//                        onTextChanged = updateBackText
//                    )
//                }
            }
        }
    }

}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    description: String = "",
    text: String,
    onClick: (RecallQuality) -> Unit,

    ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        StudyingCard(
            text = text,
            onClick = onClick
        )


    }
}

@Composable
fun StudyingCard(
    text: String,
    onClick: (RecallQuality) -> Unit,
    minHeight: Int = 288,
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
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
                        count = options.size
                    ),
                    onClick = { onClick(label.first) },
                    selected = false,
                    label = { Text(label.second) },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = color,
                        inactiveContainerColor = color
                    )
                )
            }
        }
    }



}