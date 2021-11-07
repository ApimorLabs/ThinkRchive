package work.racka.thinkrchive.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.theme.*
import work.racka.thinkrchive.utils.Sort

@ExperimentalMaterialApi
@Composable
fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    currentSortOption: Int,
    onSettingsClicked: () -> Unit = { },
    onSortOptionClicked: (Int) -> Unit = { },
    onAboutClicked: () -> Unit = { },
    onDonateClicked: () -> Unit = { },
) {

    Surface(
        shape = BottomSheetShape,
        color = MaterialTheme.colorScheme.surface,
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
                sheetTitle = "Sort By",
                onCloseClicked = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onSettingsClicked = {
                    scope.launch {
                        sheetState.hide()
                        onSettingsClicked()
                    }
                }
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                items(Sort.values()) { item ->
                    val selectedColor by animateColorAsState(
                        targetValue = if (currentSortOption == item.sortValue) {
                            MaterialTheme.colorScheme.primaryContainer
                                .copy(alpha = .6f)
                        } else Color.Transparent,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (currentSortOption == item.sortValue) {
                            LightDark
                                .copy(alpha = .9f)
                        } else MaterialTheme.colorScheme.onBackground,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    )

                    SheetOption(
                        modifier = Modifier
                            .fillMaxWidth(),
                        sortOptionName = item.type,
                        icon = item.icon,
                        onOptionClicked = {
                            onSortOptionClicked(item.sortValue)
                        },
                        selectedSortColor = selectedColor,
                        contentColor = contentColor
                    )
                }

                item {
                    DonateAndAbout(
                        modifier = Modifier
                            .padding(Dimens.MediumPadding.size),
                        onAboutClicked = {
                            scope.launch {
                                sheetState.hide()
                                onAboutClicked()
                            }
                        },
                        onDonateClicked = {
                            scope.launch {
                                sheetState.hide()
                                onDonateClicked()
                            }
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun TopSheetSection(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = { },
    onSettingsClicked: () -> Unit = { },
    settingsButtonVisible: Boolean = true,
    sheetTitle: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(height = 5.dp, width = 32.dp)
                .background(
                    color = MaterialTheme.colorScheme.onBackground
                        .copy(alpha = .1f),
                    shape = CircleShape
                )
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = onCloseClicked) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = sheetTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            IconButton(
                onClick = onSettingsClicked,
                enabled = settingsButtonVisible
            ) {
                if (settingsButtonVisible) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.settings_icon),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun SheetOption(
    modifier: Modifier = Modifier,
    sortOptionName: String,
    style: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    maxLines: Int = 1,
    iconDescription: String? = null,
    icon: ImageVector,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    onOptionClicked: () -> Unit = { },
    selectedSortColor: Color = Color.Transparent
) {

    Box(
        modifier = modifier
            .clip(Shapes.large)
            .background(
                color = selectedSortColor,
                shape = Shapes.large
            )
            .clickable { onOptionClicked() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.MediumPadding.size)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = contentColor,
            )
            Spacer(modifier = Modifier.width(Dimens.MediumPadding.size))
            Text(
                text = sortOptionName,
                style = style,
                color = contentColor,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DonateAndAbout(
    modifier: Modifier = Modifier,
    onDonateClicked: () -> Unit = { },
    onAboutClicked: () -> Unit = { }
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onDonateClicked,
            shape = CircleShape,
            elevation = ButtonDefaults
                .buttonElevation(
                    defaultElevation = 0.dp
                ),
            modifier = Modifier
                .weight(1f)
        ) {
            Icon(
                imageVector = Icons.Outlined.VolunteerActivism,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Donate",
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                        vertical = Dimens.SmallPadding.size
                    ),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(Dimens.MediumPadding.size))
        Button(
            onClick = onAboutClicked,
            shape = CircleShape,
            elevation = ButtonDefaults
                .buttonElevation(
                    defaultElevation = 0.dp
                ),
            modifier = Modifier
                .weight(.7f)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "About",
                modifier = Modifier
                    .padding(
                    horizontal = 4.dp,
                    vertical = Dimens.SmallPadding.size
                ),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun BottomSheetPreview() {
    ThinkRchiveTheme {
        HomeBottomSheet(
            scope = rememberCoroutineScope(),
            sheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden),
            currentSortOption = 1
        )
    }
}

@Preview
@Composable
fun SortOptionPreview() {
    ThinkRchiveTheme {
        SheetOption(sortOptionName = "Alphabetical", icon = Icons.Outlined.SortByAlpha)
    }
}
