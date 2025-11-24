package com.example.jobfinder.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.jobfinder.R
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.utils.ThemePreviews

@Composable
fun JobSearchTitleItem(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_large),
                top = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_large)
            ),
        text = text,
        color = MaterialTheme.colorScheme.inverseSurface,
        style = MaterialTheme.typography.titleMedium,
    )
}

@ThemePreviews
@Composable
fun JobSearchTitleItemPreview() {
    JobFinderTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            JobSearchTitleItem(
                //text = stringResource(id = R.string.job_results_title)
                text = "Jobs in Dublin"
                // or simply: text = "Jobs in Dublin"
            )
        }
    }
}
