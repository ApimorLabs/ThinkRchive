package work.racka.thinkrchive.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

// Must always be used inside a BoxScope for proper alignment
@ExperimentalAnimationApi
@Composable
fun ToolbarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {

    var imageLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = imageUrl,
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

    Image(
        painter = coilPainter,
        contentDescription = stringResource(id = R.string.thinkpad_image),
        modifier = modifier
            .padding(Dimens.MediumPadding.size)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
    )
    AnimatedVisibility(
        visible = imageLoading,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        CircularProgressIndicator()
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun TopCardWithImagePreview() {
    ThinkRchiveTheme {
        ToolbarImage(
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
        )
    }
}
