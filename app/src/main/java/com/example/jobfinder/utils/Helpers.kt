package com.example.flightsearchapp.utils

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RippleConfiguration
import androidx.compose.ui.graphics.Brush
import com.example.flightsearchapp.ui.theme.starIconGradient

@OptIn(ExperimentalMaterial3Api::class)
val starIconRippleConfiguration =
    RippleConfiguration(
        color = starIconGradient.last(),
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0.05f,
            focusedAlpha = 0.0f,
            draggedAlpha = 0.0f,
            hoveredAlpha = 0.0f
        )
    )

val brush = Brush.linearGradient(
    starIconGradient
)