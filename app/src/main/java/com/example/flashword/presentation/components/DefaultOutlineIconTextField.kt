package com.example.flashword.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashword.ui.theme.FlashWordTheme

@Composable
fun DefaultOutlineIconTextField (
    modifier: Modifier = Modifier,
    value: String = "Value",
    onValueChange: (String) -> Unit = {},
    labelText: String = "label",
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText)},
        leadingIcon = {if (leadingIcon != null) Icon(imageVector = leadingIcon,null)},
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(30),

        isError = isError,
        supportingText = { if (errorMessage != null) Text(errorMessage)}

    )
}

@Composable
@Preview(showBackground = true)
fun DefaultOutlineIconTextFieldPreview() {
    FlashWordTheme {
        DefaultOutlineIconTextField (isError = true, errorMessage = "invalid email")
    }

}