package com.example.jobfinder.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jobfinder.R
import com.example.jobfinder.ui.theme.FlightSearchAppTheme
import com.example.jobfinder.utils.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(modifier: Modifier = Modifier) {

    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.app_name).uppercase(),
                modifier = modifier
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    )
}

@ThemePreviews
@Composable
fun FlightSearchTopAppBarPreview() {
    FlightSearchAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            FlightSearchTopAppBar()
        }
    }
}