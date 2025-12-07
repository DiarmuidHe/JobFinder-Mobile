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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.jobfinder.R
import androidx.compose.ui.unit.dp
@Composable
fun JobSearchTextField(
    modifier: Modifier = Modifier,
    searchText: String,                       // Current text entered by the user
    onSearchTextChange: (String) -> Unit      // Callback triggered when text changes
) {
    // Tracks user interactions (click, focus, etc.)
    val interactionSource = remember { MutableInteractionSource() }

    // Rounded card container for the search field
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        shape = CircleShape
    ) {
        TextField(
            value = searchText,                // Text shown inside the field
            onValueChange = onSearchTextChange,
            interactionSource = interactionSource,
            placeholder = { Text("Search jobs…") },  // Hint text
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,              // Subtle border around the search box
                    brush = Brush.horizontalGradient(
                        listOf(Color.DarkGray, Color.DarkGray)
                    ),
                    shape = CircleShape
                )
        )
    }
}
