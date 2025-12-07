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
import com.example.jobfinder.utils.brush
import com.example.jobfinder.utils.starIconRippleConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteButton(
    onFavoriteJobClicked: (FavoriteJob) -> Unit,          // Called when the star is tapped
    isFavoriteButtonFilled: (FavoriteJob) -> Boolean,     // Checks if job is already favorited
    favoriteJob: FavoriteJob,                             // The job this button belongs to
    modifier: Modifier = Modifier
) {
    // Custom ripple effect for the star icon
    CompositionLocalProvider(LocalRippleConfiguration provides starIconRippleConfiguration) {
        IconButton(
            modifier = modifier,
            onClick = {
                onFavoriteJobClicked(favoriteJob)          // Toggle favorite state
                isFavoriteButtonFilled(favoriteJob)        // Trigger UI update
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_standard))
                    .let {
                        // If the job is favorited, color the star using a brush overlay
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
                        // Otherwise, show default gray star
                        it
                    },
                imageVector = Icons.Default.Star,
                tint = Color.LightGray,
                contentDescription = stringResource(id = R.string.favorite)
            )
        }
    }
}


