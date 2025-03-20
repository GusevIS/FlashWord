package com.example.flashword.presentation.dashboard.deck

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.flashword.R
import com.example.flashword.ui.theme.FlashWordTheme
import com.example.flashword.ui.theme.md_theme_light_success

@Composable
fun DeckCard(
    cardSet: DeckState,
    onClick: (String, String) -> Unit = { _: String, _: String -> },
    onAddClick: (String, String) -> Unit = { _: String, _: String -> },
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 6.dp, y = (-6).dp)
                .zIndex(0f)
            ,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            ),
        ) {
            Spacer(modifier = Modifier.height(150.dp))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick(cardSet.deckId, cardSet.title) })
                .zIndex(1f),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = cardSet.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    IconButton(onClick = { onAddClick(cardSet.deckId, cardSet.title) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Row {
                    Text(
                        text = stringResource(R.string.new_cards_count) + " " + cardSet.newCards.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.cards_to_learn_count) + " " + cardSet.cardsToStudy.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.cards_to_review_count) + " " + cardSet.cardsToReview.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Row {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_access_time_filled_24),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = if ((cardSet.newCards + cardSet.cardsToStudy + cardSet.cardsToReview) != 0) MaterialTheme.colorScheme.error else md_theme_light_success
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = cardSet.todayDue.toString() + " " + stringResource(R.string.today_due),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if ((cardSet.newCards + cardSet.cardsToStudy + cardSet.cardsToReview) != 0) MaterialTheme.colorScheme.error else md_theme_light_success
                    )
                }

                LinearProgressIndicator(
                    progress = { cardSet.todayProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = if ((cardSet.newCards + cardSet.cardsToStudy + cardSet.cardsToReview) != 0) MaterialTheme.colorScheme.error else md_theme_light_success
                )

            }
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun CardSetItemPreview() {
//    FlashWordTheme {
//        DeckCard(
//            DeckState("English verbs", 81, 12, 8, 35, 0.7f, 45)
//        )
//    }
//}
