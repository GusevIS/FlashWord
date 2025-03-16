package com.example.flashword.presentation.profile

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flashword.R
import com.example.flashword.ui.theme.FlashWordTheme
import com.example.flashword.ui.theme.setStatusBarColor

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState = ProfileState(),
    navController: NavController,

    onEditClick: () -> Unit = {},
    
    onLogoutClick: () -> Unit,
) {
    setStatusBarColor(MaterialTheme.colorScheme.primary.toArgb())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileHeader(
            userName = state.userName,
            email = state.email,
            onEditClick = onEditClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileContent(

            onLogoutClick = onLogoutClick
        )

    }
}

@Composable
fun ProfileHeader(
    userName: String = "testuser",
    email: String = "testemail@gmail.com",
    onEditClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFA1887F),
                            Color(0xFFFF8A65),
                            //Color(0xFFFFD180),
                        )
                    )
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 16.dp)
            ) {
                Image(
                    ImageVector.vectorResource(R.drawable.user_outlined_24),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )

                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Button(
                        onClick = onEditClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)), // Полупрозрачная кнопка
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(24.dp)
                            .width(58.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.edit),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

            }

        }
    }

}

@Composable
fun ProfileContent(
    onLanguageClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onSyncClick: () -> Unit = {},
    onRepetitionClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            textContentColor = MaterialTheme.colorScheme.onBackground,
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(stringResource(R.string.confirm_logout)) },
            //text = { Text("Your progress may not be saved.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onLogoutClick()
                        showLogoutDialog = false
                    },
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item { SettingsItem(title = stringResource(R.string.language), onClick = onLanguageClick, icon = ImageVector.vectorResource(R.drawable.baseline_language_24)) }
        item { SettingsItem(title = stringResource(R.string.theme), onClick = onThemeClick, icon = ImageVector.vectorResource(R.drawable.baseline_brightness_6_24)) }
        item { HorizontalDivider(color = MaterialTheme.colorScheme.tertiary) }

        item { SettingsItem(title = stringResource(R.string.notifications), onClick = onNotificationsClick, icon = Icons.Default.Notifications) }
        item { SettingsItem(title = stringResource(R.string.sync), onClick = onSyncClick, icon = ImageVector.vectorResource(R.drawable.baseline_cloud_sync_24)) }
        item { SettingsItem(title = stringResource(R.string.repetition_settings), onClick = onRepetitionClick, icon = ImageVector.vectorResource(R.drawable.cards_blank_outlined_24)) }
        item { HorizontalDivider(color = MaterialTheme.colorScheme.tertiary) }

        item { SettingsItem(title = stringResource(R.string.about), onClick = onAboutClick, icon = ImageVector.vectorResource(R.drawable.baseline_info_outline_24)) }
        item { SettingsItem(title = stringResource(R.string.log_out), onClick = { showLogoutDialog = true }, isLogout = true, icon = ImageVector.vectorResource(R.drawable.baseline_logout_24)) }
    }
}

@Composable
fun SettingsItem(
    title: String,
    onClick: () -> Unit,
    isLogout: Boolean = false,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isLogout) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isLogout) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun ProfileHeaderPreview() {
    FlashWordTheme {
        ProfileHeader()
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProfileContentPreview() {
    FlashWordTheme {
        ProfileContent()
    }
}