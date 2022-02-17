package work.racka.thinkrchive.ui.components.qonversion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.qonversion.android.sdk.dto.offerings.QOffering
import work.racka.thinkrchive.ui.components.ClickableEntry
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes

fun LazyListScope.QonVersionEntries(
    modifier: Modifier = Modifier,
    offerings: List<QOffering>,
    hasPremium: Boolean,
    onOfferingClick: (QOffering) -> Unit = { }
) {
    if (hasPremium) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(Shapes.large)
                    .background(Color.Green)
            ) {
                Text(
                    text = "You have Premium!",
                    modifier = Modifier
                        .padding(Dimens.MediumPadding.size)
                )
            }
        }
    }

    items(offerings) { offering ->
        ClickableEntry(
            modifier = modifier
                .padding(
                    horizontal = Dimens.MediumPadding.size,
                    vertical = Dimens.SmallPadding.size
                ),
            title = offering.offeringID,
            subtitle = "Empty",
            description = "Empty",
            onEntryClick = {
                onOfferingClick(offering)
            }
        )
    }
}