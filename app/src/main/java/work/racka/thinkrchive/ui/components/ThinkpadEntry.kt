package work.racka.thinkrchive.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants

@Composable
fun ThinkpadEntry(
    modifier: Modifier = Modifier,
    onEntryClick: () -> Unit = {},
    thinkpad: Thinkpad
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 1.15f)
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

    var imageLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = thinkpad.imageUrl,
        builder = {
            crossfade(true)
            listener(
                onStart = {
                    imageLoading = true
                },
                onSuccess = { _, _ ->
                    imageLoading = false
                }
            )
        }
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = animatedModifier
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(color = MaterialTheme.colors.surface)
            .clickable { onEntryClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (imageLoading) {
                    LoadingImage(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                }
                Image(
                    painter = coilPainter,
                    contentDescription = "${thinkpad.model} Image",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(Dimens.MediumPadding.size)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        top = Dimens.MediumPadding.size,
                        bottom = Dimens.MediumPadding.size,
                        end = Dimens.MediumPadding.size
                    )
            ) {
                Text(
                    text = thinkpad.model,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SubtitleText(
                    subtitleName = "Platform",
                    subtitleData = thinkpad.processorPlatforms
                )
                SubtitleText(
                    subtitleName = "Market Value",
                    subtitleData = marketPrice
                )
                SubtitleText(
                    subtitleName = "Release",
                    subtitleData = thinkpad.releaseDate
                )
            }
        }
    }
}

@Composable
fun SubtitleText(
    verticalPadding: Dp = 4.dp,
    subtitleName: String,
    subtitleData: String,
    style: TextStyle = MaterialTheme.typography.body2,
    maxLines: Int = 1,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(vertical = verticalPadding)
    ) {
        Text(
            text = "$subtitleName:",
            style = style,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = subtitleData,
            style = style,
            color = MaterialTheme.colors.onSurface,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadingImage(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.15f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.onSurface
                    .copy(alpha = alpha),
                shape = Shapes.large
            )
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SubtitleTextPreview() {
    ThinkRchiveTheme {
        SubtitleText(
            subtitleName = "Market Price",
            subtitleData = "$250 - $2050"
        )
    }
}

@Preview
@Composable
private fun ThinkpadEntryPreview() {
    ProvideWindowInsets {
        ThinkRchiveTheme {
            Constants.ThinkpadsListPreview[0].apply {
                ThinkpadEntry(
                    thinkpad = this,
                    modifier = Modifier
                        .padding(Dimens.MediumPadding.size)
                        .statusBarsPadding()
                )
            }
        }
    }
}
