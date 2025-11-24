package com.example.jobfinder.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteRoute
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.ui.theme.gradientColors
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.fakeAirportsData

@Composable
fun FlightSearchTextField(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus(true)
            }
            .shadow(
                ambientColor = gradientColors.last(),
                spotColor = Color.Cyan,
                elevation = if (isFocused) 15.dp else 0.dp,
                clip = true,
                shape = CircleShape
            ),
        shape = CircleShape
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            interactionSource = interactionSource,
            placeholder = { Text(text = stringResource(R.string.user_input)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        listOf(Color.Cyan, Color.Blue)
                    ),
                    shape = CircleShape
                )
                .focusRequester(focusRequester)
        )
    }
}

@ThemePreviews
@Composable
fun FlightSearchTextFieldPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            FlightSearchTextField(
                searchText = stringResource(id = R.string.user_input),
                onSearchTextChange = {}
            )
        }
    }
}
