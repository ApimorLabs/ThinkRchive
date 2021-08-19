package work.racka.thinkrchive.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants

@Composable
fun DetailsCards(
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad,
    onExternalLinkClick: () -> Unit = { }
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThinkpadDetails(
            thinkpad = thinkpad,
            onExternalLinkClick = onExternalLinkClick
        )
        ThinkpadFeatures(thinkpad = thinkpad)
    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailsCardsPreview() {
    ThinkRchiveTheme {
        DetailsCards(thinkpad = Constants.ThinkpadsListPreview[0])
    }
}
