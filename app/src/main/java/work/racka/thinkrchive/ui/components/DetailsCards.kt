package work.racka.thinkrchive.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import work.racka.thinkrchive.R
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.theme.BlueLinkColor
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
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
    }

}

@Composable
fun ThinkpadDetails(
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad,
    onExternalLinkClick: () -> Unit = { }
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

    val divider = " - "
    val marketPrice by remember(thinkpad) {
        derivedStateOf {
            buildAnnotatedString {
                append("$${thinkpad.marketPriceStart}")
                append(divider)
                append("$${thinkpad.marketPriceEnd}")
            }.text
        }
    }

    var maxLines by remember {
        mutableStateOf(1)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180F else 0F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = animatedModifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = thinkpad.model,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontal = Dimens.MediumPadding.size)
            )
            IconButton(
                onClick = onExternalLinkClick,
                modifier = Modifier
                    .padding(horizontal = Dimens.SmallPadding.size)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Launch,
                    contentDescription = stringResource(id = R.string.external_link),
                    tint = BlueLinkColor
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = Shapes.large
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimens.MediumPadding.size)
            ) {
                SubtitleTextWithIcon(
                    subtitleName = "Series",
                    subtitleData = thinkpad.series,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Category,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Release Date",
                    subtitleData = thinkpad.releaseDate,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Today,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Market Value",
                    subtitleData = marketPrice,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Loyalty,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Platform",
                    subtitleData = thinkpad.processorPlatforms,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.DeveloperBoard,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Processors",
                    subtitleData = thinkpad.processors,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Memory,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Graphics",
                    subtitleData = thinkpad.graphics,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Dvr,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Display Res",
                    subtitleData = thinkpad.displayRes,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.Laptop,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Max RAM",
                    subtitleData = thinkpad.maxRam,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Rounded.ViewAgenda,
                    maxLines = maxLines
                )
            }
        }

        IconButton(
            onClick = {
                maxLines = if (maxLines == 1) {
                    expanded = true
                    Int.MAX_VALUE
                } else {
                    expanded = false
                    1
                }
            },
            modifier = Modifier
                .padding(
                    top = Dimens.SmallPadding.size,
                    end = Dimens.SmallPadding.size
                )
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Rounded.ExpandMore,
                contentDescription = stringResource(id = R.string.expand_icon),
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.rotate(angle)
            )
        }
    }
}

@Composable
fun SubtitleTextWithIcon(
    subtitleName: String,
    subtitleData: String,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    maxLines: Int = 1,
    iconDescription: String? = null,
    icon: ImageVector
) {
   Row(
       horizontalArrangement = Arrangement.Start,
       modifier = Modifier
           .padding(vertical = 4.dp)
   ) {
       Icon(
           imageVector = icon,
           contentDescription = iconDescription,
           tint = MaterialTheme.colors.onSurface,
       )
       Spacer(modifier = Modifier.width(Dimens.MediumPadding.size))
       SubtitleText(
           subtitleName = subtitleName,
           subtitleData = subtitleData,
           style = style,
           maxLines = maxLines,
           verticalAlignment = Alignment.Top
       )
   }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ThinkpadDetailsPreview() {
    ThinkRchiveTheme {
        ThinkpadDetails(thinkpad = Constants.ThinkpadsListPreview[0])
    }
}
