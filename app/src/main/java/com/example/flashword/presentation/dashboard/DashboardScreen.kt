package com.example.flashword.presentation.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.flashword.R
import com.example.flashword.presentation.dashboard.deck.DeckCard
import com.example.flashword.ui.theme.setStatusBarColor

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardUiState,
    onReviewAllClick: () -> Unit = {},
    onAddCardClick: (String) -> Unit = {},
    onAddDeckClick: (String) -> Unit = {},
) {
    setStatusBarColor(MaterialTheme.colorScheme.background.toArgb())

    DashboardScreenContent(
        state = state,
        onReviewAllClick = onReviewAllClick,
        onAddCardClick = onAddCardClick,
        onAddDeckClick = onAddDeckClick,
    )
}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    state: DashboardUiState,
    onReviewAllClick: () -> Unit = {},
    onAddCardClick: (String) -> Unit = {},
    onAddDeckClick: (String) -> Unit = {},
) {
    var showAddDeckDialog by remember { mutableStateOf(false) }

    if (showAddDeckDialog) {
        DialogWithTextField(
            onDismissRequest = { showAddDeckDialog = !showAddDeckDialog },
            onConfirmation = { text ->
                showAddDeckDialog = !showAddDeckDialog
                onAddDeckClick(text)
            }
        )
    }

    var searchQuery by remember { mutableStateOf("") }

    val filteredCardDecks = remember(searchQuery, state) {
        state.cardDecks.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.dashboard_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(stringResource(R.string.search_decks)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear text")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = colors(
                    focusedTextColor = Color(0xFF4A4A4A),
                    unfocusedTextColor = Color(0xFF4A4A4A),
                    disabledTextColor = Color.Gray,
                    errorTextColor = Color.Red,
                    focusedContainerColor = Color(0xFFFCEED7),
                    unfocusedContainerColor = Color(0xFFFCEED7),
                    focusedBorderColor = Color(0xFFD1C4B3),
                    unfocusedBorderColor = Color(0xFFD1C4B3),
                    cursorColor = Color(0xFF8B8574),
                    focusedLeadingIconColor = Color(0xFF8B8574),
                    unfocusedLeadingIconColor = Color(0xFF8B8574),
                    focusedTrailingIconColor = Color(0xFF8B8574),
                    unfocusedTrailingIconColor = Color(0xFF8B8574),
                    focusedLabelColor = Color(0xFF8B8574),
                    unfocusedLabelColor = Color(0xFF8B8574)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .height(148.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.error,
                        ),
                    ) {
                        Row {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.todays_plan),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(Modifier.width(16.dp))

                                Text(
                                    text = state.totalCardsToReview.toString() + " " + stringResource(R.string.total_cards_count),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )

                                Spacer(Modifier.width(16.dp))

                                Button(
                                    onClick = onReviewAllClick,
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(32.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                                ) {
                                    Text(
                                        text = stringResource(R.string.start_reviewing_all),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                            }

                            Image(
                                painter = painterResource(id = R.drawable.cards_pic),  // Имя файла без расширения
                                contentDescription = "Cards",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(filteredCardDecks) { cardDeck ->
                    DeckCard(
                        cardDeck,

                        onAddClick = onAddCardClick
                        )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                showAddDeckDialog = !showAddDeckDialog
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add new deck",
                tint = Color.White
            )
        }

    }

}

@Composable
fun DialogWithTextField(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.dashboard_add_deck_description),
                    modifier = Modifier.padding(16.dp),
                )

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    colors = TextFieldDefaults.colors(
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .padding(8.dp)
                        .focusRequester(focusRequester),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onBackground)
                    }
                    TextButton(
                        onClick = { onConfirmation(text.trim()) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.ok), color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}