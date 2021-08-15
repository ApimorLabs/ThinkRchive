package work.racka.thinkrchive.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@Composable
fun ThinkpadEntry(
    onEntryClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad
) {
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

    var isLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = thinkpad.imageUrl,
        builder = {
            crossfade(true)
            listener(
                onStart = {
                    isLoading = true
                },
                onSuccess = { _, _ ->
                    isLoading = false
                }
            )
        }
    )
    
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = Shapes.large
            )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center) {
                if (isLoading) {
                    LoadingImage(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                } else {
                    Image(
                        painter = coilPainter,
                        contentDescription = "${thinkpad.model} Image",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                }
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SubtitleText(
                    subtitleName = "Platform",
                    subtitleData = thinkpad.processorPlatforms
                )
                SubtitleText(
                    subtitleName = "Current BIOS",
                    subtitleData = thinkpad.biosVersion
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
private fun SubtitleText(
    subtitleName: String,
    subtitleData: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$subtitleName:",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = subtitleData,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadingImage(
    modifier: Modifier = Modifier
        .size(120.dp)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary
                    .copy(alpha = alpha),
                shape = Shapes.large
            )
    )
}

@Preview(
    showBackground = true
)
@Composable
fun SubtitleTextPreview() {
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
    ThinkRchiveTheme {
//        val thinkpad = Thinkpad(
//            model = "Thinkpad T450",
//            imageUrl =
//        )
//        ThinkpadEntry(thinkpad = )
    }
}
