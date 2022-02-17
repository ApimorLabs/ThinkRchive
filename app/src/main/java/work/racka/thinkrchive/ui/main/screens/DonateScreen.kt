package work.racka.thinkrchive.ui.main.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.android.billingclient.api.SkuDetails
import com.qonversion.android.sdk.dto.offerings.QOffering
import work.racka.thinkrchive.ui.components.ClickableEntry
import work.racka.thinkrchive.ui.components.CollapsingToolbarBase
import work.racka.thinkrchive.ui.components.qonversion.QonVersionEntries
import work.racka.thinkrchive.ui.main.viewModel.QonversionViewModel
import work.racka.thinkrchive.ui.theme.Dimens

@ExperimentalMaterial3Api
@Composable
fun DonateScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    skuList: List<SkuDetails>,
    qonViewModel: QonversionViewModel,
    onDonateItemClicked: (SkuDetails) -> Unit = { },
    onBackButtonPressed: () -> Unit = { },
    onOfferingClicked: (QOffering) -> Unit = { }
) {
    // CollapsingToolbar Implementation
    val toolbarHeight = 250.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "Donate",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = onBackButtonPressed
            ) {
                Text(
                    text = "Donate",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(horizontal = Dimens.SmallPadding.size)
                        .animateContentSize(
                            animationSpec = tween(
                                300,
                                easing = LinearOutSlowInEasing
                            )
                        )
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = Dimens.MediumPadding.size)
                .nestedScroll(nestedScrollConnection),
            state = listState
        ) {

            QonVersionEntries(
                offerings = qonViewModel.offerings,
                hasPremium = qonViewModel.hasPremium,
                onOfferingClick = {
                    onOfferingClicked(it)
                }
            )

            items(skuList) { item ->
                ClickableEntry(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.MediumPadding.size,
                            vertical = Dimens.SmallPadding.size
                        ),
                    title = item.title,
                    subtitle = item.price,
                    description = item.description,
                    onEntryClick = { onDonateItemClicked(item) }
                )
            }
        }

    }

}