package work.racka.thinkrchive.ui.main.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import work.racka.thinkrchive.ui.components.ClickableEntry
import work.racka.thinkrchive.ui.components.CollapsingToolbarBase
import work.racka.thinkrchive.ui.theme.Dimens

@Composable
fun DonateScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    skuList: List<SkuDetails>,
    onDonateItemClicked: (SkuDetails) -> Unit = { },
    onBackButtonPressed: () -> Unit = { }
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
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h3,
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