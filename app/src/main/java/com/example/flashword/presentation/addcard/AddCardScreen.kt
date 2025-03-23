package com.example.flashword.presentation.addcard

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashword.R
import com.example.flashword.ui.theme.FlashWordTheme
import com.example.flashword.ui.theme.md_theme_light_success
import com.example.flashword.ui.theme.setStatusBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    state: AddCardState ,

    onPopBackClick: () -> Unit,

    onTurnOverCardClick: () -> Unit = {},
    onAddClick: () -> Unit = {},

    updateFrontText: (String) -> Unit = {},
    updateBackText: (String) -> Unit = {},
) {
    setStatusBarColor(MaterialTheme.colorScheme.background.toArgb())

    Column {
        CenterAlignedTopAppBar (
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
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

        AddCardScreenContent(
            state.isBackSide,
            state.frontText,
            state.backText,

            onTurnOverCardClick = onTurnOverCardClick,
            onAddClick = onAddClick,

            updateFrontText = updateFrontText,
            updateBackText = updateBackText,

        )
    }

}

@Composable
fun AddCardScreenContent(
    isBackSide: Boolean = false,

    frontText: String = "",
    backText: String = "",

    onTurnOverCardClick: () -> Unit = {},
    onAddClick: () -> Unit = {},

    updateFrontText: (String) -> Unit = {},
    updateBackText: (String) -> Unit = {},
) {
    LazyColumn {

        item {
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
                        cameraDistance = 24 * density
                    },
                contentAlignment = Alignment.Center
            ) {
                if (animatedRotation <= 90f) {
                    Card(
                        description = stringResource(R.string.enter_front_desc),
                        text = frontText,
                        onTextChanged = updateFrontText
                    )
                } else {
                    Card(
                        description = stringResource(R.string.enter_back_desc),
                        modifier = Modifier.graphicsLayer { scaleX = -1f },
                        text = backText,
                        onTextChanged = updateBackText
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    ElevatedButton(
                        onClick = onTurnOverCardClick,
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

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    ElevatedButton(
                        onClick = onAddClick,
                        colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_success),
                        elevation = ButtonDefaults.buttonElevation(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "add",
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    description: String = "",
    text: String = "",
    onTextChanged: (String) -> Unit = {},

) {
    Card(
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RoundedCornerShape(12.dp)
    ) {

        ExpandingBasicTextField(
            description = description,
            text = text,
            onTextChanged = onTextChanged
        )


    }
}

@Composable
fun ExpandingBasicTextField(
    description: String = "",
    text: String = "",
    onTextChanged: (String) -> Unit,
    minHeight: Int = 168,
) {
    BasicTextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
                        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .heightIn(min = minHeight.dp),
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
        maxLines = Int.MAX_VALUE,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(4.dp)
            ) {
                if (text.isEmpty()) {
                    Text(description, color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddCardScreenContentPreview() {
    FlashWordTheme {
        AddCardScreenContent()
    }
}