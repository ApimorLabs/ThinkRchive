package work.racka.thinkrchive.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.racka.thinkrchive.ui.theme.*
import work.racka.thinkrchive.utils.Sort

@Composable
fun SettingsEntry(
    modifier: Modifier = Modifier,
    settingsEntryName: String,
    onSettingsEntryClick: () -> Unit = { },
    currentSettingIcon: ImageVector = Icons.Outlined.SettingsSuggest,
    currentSettingValue: String
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = animatedModifier
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(color = MaterialTheme.colors.surface)
            .clickable { onSettingsEntryClick() }
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.UpperMediumPadding.size,
                vertical = Dimens.MediumPadding.size
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = settingsEntryName,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = currentSettingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
                Text(
                    text = currentSettingValue,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = Dimens.SmallPadding.size)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SettingsEntryBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    currentTheme: Int,
    onThemeOptionClicked: (Int) -> Unit = { }
) {
    Surface(
        shape = BottomSheetShape,
        color = MaterialTheme.colors.surface,
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.SmallPadding.size)
                .navigationBarsPadding()
        ) {
            TopSheetSection(
                modifier = Modifier
                    .padding(Dimens.SmallPadding.size),
                sheetTitle = "Choose Your Theme",
                onCloseClicked = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                settingsButtonVisible = false
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                items(Theme.values()) { item ->
                    val selectedColor by animateColorAsState(
                        targetValue = if (currentTheme == item.themeValue) {
                            MaterialTheme.colors.primary
                                .copy(alpha = .6f)
                        } else Color.Transparent,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (currentTheme == item.themeValue) {
                            LightDark
                                .copy(alpha = .9f)
                        } else MaterialTheme.colors.onBackground,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )

                    SheetOption(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sortOptionName = item.themeName,
                        icon = item.icon,
                        onSortOptionClicked = {
                            onThemeOptionClicked(item.themeValue)
                        },
                        selectedSortColor = selectedColor,
                        contentColor = contentColor
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun SettingsEntrySheetPrev() {
    ThinkRchiveTheme {
        SettingsEntryBottomSheet(
            scope = rememberCoroutineScope(),
            sheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
            currentTheme = 1
        )
    }
}

@Preview
@Composable
private fun SettingsEntryPrev() {
    ThinkRchiveTheme {
        SettingsEntry(
            settingsEntryName = "Theme Settings",
            currentSettingValue = "Dark Mode"
        )
    }
}
