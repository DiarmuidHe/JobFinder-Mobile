package com.example.jobfinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.jobfinder.R
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.ui.theme.blackWhiteGradient
import com.example.jobfinder.utils.ThemePreviews

@Composable
fun NoFavoritesScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_extra_large)),
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(alpha = 0.4f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = blackWhiteGradient
                            ),
                            blendMode = BlendMode.SrcAtop
                        )
                    }
                }
                .size(dimensionResource(id = R.dimen.icon_large))
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Text(
            text = stringResource(id = R.string.no_favorites),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.inverseSurface,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(id = R.string.add_routes),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.inverseSurface,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@ThemePreviews
@Composable
fun NoFavoritesScreenPreview() {
    JobFinderTheme {
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            NoFavoritesScreen()
        }
    }
}