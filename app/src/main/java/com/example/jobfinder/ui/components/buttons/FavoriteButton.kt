package com.example.jobfinder.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.jobfinder.R
import com.example.jobfinder.model.FavoriteJob
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.utils.ThemePreviews
import com.example.jobfinder.utils.brush
import com.example.jobfinder.utils.starIconRippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteButton(
    onFavoriteJobClicked: (FavoriteJob) -> Unit,
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean,
    favoriteJob: FavoriteJob,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalRippleConfiguration provides starIconRippleConfiguration) {
        IconButton(
            modifier = modifier,
            onClick = {
                onFavoriteJobClicked(favoriteJob)
                isFavoriteButtonFilled(favoriteJob)
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_standard))
                    .let {
                        if (isFavoriteButtonFilled(favoriteJob)) {
                            return@let it
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(
                                            brush = brush,
                                            blendMode = BlendMode.SrcAtop
                                        )
                                    }
                                }
                        }
                        it
                    },
                imageVector = Icons.Default.Star,
                tint = Color.LightGray,
                contentDescription = stringResource(id = R.string.favorite)
            )
        }
    }
}

@ThemePreviews
@Composable
fun FavoriteIconPreview() {
    JobFinderTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            FavoriteButton(
                onFavoriteJobClicked = {},
                isFavoriteButtonFilled = { true },
                favoriteJob = FavoriteJob(
                    jobId = "123",
                    title = "Android Developer",
                    company = "Example Corp",
                    location = "Remote",
                    description = "Build and maintain Android applications using Kotlin and Jetpack libraries.",
                    skills = "Kotlin, Android Studio, Jetpack Compose, REST APIs, Git",
                    image = "https://moynecs.ie/uploads/7/3/9/2/73928041/6013364_orig.jpg"
                )

            )
        }
    }
}
