package com.example.flightsearchapp.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Custom annotation to encapsulate the code for both Light and Dark themes,
 * so that less code is needed to build a Preview for the Composables across the app.
 * Just call @ThemePreviews instead of @Preview.
 *
 * The original idea: https://medium.com/@mortitech/better-previews-in-compose-with-custom-annotations-dc49b94ff579
 */

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
annotation class ThemePreviews