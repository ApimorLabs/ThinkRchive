package work.racka.thinkrchive.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@Composable
fun SettingsEntry(
    modifier: Modifier = Modifier,
    settingsEntryName: String,
    onSettingsEntryClick: (String) -> Unit = { },
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
            .clickable { onSettingsEntryClick(settingsEntryName) }
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
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = Dimens.SmallPadding.size)
                )
            }
        }
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
