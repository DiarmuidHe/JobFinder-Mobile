package com.example.jobfinder.ui.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.ui.theme.gradientColors
import com.example.jobfinder.utils.ThemePreviews

@Composable
fun ArrowUpwardIcon(
    modifier: Modifier = Modifier
) {
    Box {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_upward),
            contentDescription = null,
            modifier = modifier
                .offset(x = (-2).dp, y = (2).dp)
                .blur(6.dp)
                .size(dimensionResource(id = R.dimen.icon_large))
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            tint = Color.Gray,
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_upward),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            ),
                            blendMode = BlendMode.SrcAtop
                        )
                    }
                }
                .size(dimensionResource(id = R.dimen.icon_large))
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@ThemePreviews
@Composable
fun ArrowUpwardIconPreview() {
    JobFinderTheme {
        ArrowUpwardIcon()
    }
}