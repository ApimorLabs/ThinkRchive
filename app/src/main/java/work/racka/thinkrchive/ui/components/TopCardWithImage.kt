package work.racka.thinkrchive.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalAnimationApi
@Composable
fun TopCardWithImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onBackButtonPressed: () -> Unit = { },
    listState: LazyListState
) {

    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }
    val scrollDp = 300 - listState.firstVisibleItemScrollOffset
    val animatedCardSize by animateDpAsState(
        targetValue = if (scrollDp < 120) 110.dp else scrollDp.dp,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )
    val animatedElevation by animateDpAsState(
        targetValue = if (scrollDp < 110) 10.dp else 0.dp,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(key1 = animatedProgress) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value
        )

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

    Box(
        modifier = animatedModifier
            .fillMaxWidth()
            .height(animatedCardSize)
            .shadow(
                elevation = animatedElevation,
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
    ) {
        IconButton(
            onClick = onBackButtonPressed,
            modifier = Modifier
                .padding(Dimens.SmallPadding.size)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.back_icon),
                tint = MaterialTheme.colors.onSurface
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = coilPainter,
                contentDescription = stringResource(id = R.string.thinkpad_image),
                modifier = Modifier
                    .size(300.dp)
                    .padding(Dimens.MediumPadding.size)
                    .animateContentSize(
                        animationSpec = tween(600, easing = LinearOutSlowInEasing)
                    )
            )
            AnimatedVisibility(
                visible = imageLoading,
                enter = expandHorizontally(),
                exit = shrinkOut()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun TopCardWithImagePreview() {
    ThinkRchiveTheme {
        TopCardWithImage(
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            listState = rememberLazyListState()
        )
    }
}
