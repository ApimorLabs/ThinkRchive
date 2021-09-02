package work.racka.thinkrchive.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.R

// Collapsing Toolbar that can be used anywhere.
// It has a back button, default bottom rounded corners
// & a box scope which holds content centered by default.
@Composable
fun TopCollapsingToolbar(
    modifier: Modifier = Modifier,
    toolbarHeading: String,
    onBackButtonPressed: () -> Unit = { },
    listState: LazyListState,
    contentAlignment: Alignment = Alignment.Center,
    shape: Shape = RoundedCornerShape(
        bottomEnd = 20.dp,
        bottomStart = 20.dp
    ),
    backgroundColor: Color = MaterialTheme.colors.surface,
    toolbarHeight: Dp = 300.dp,
    content: @Composable BoxScope.() -> Unit
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }

    var offset by remember {
        mutableStateOf(0)
    }

    // TODO: Fix Collapsing Toolbar
    LaunchedEffect(key1 = listState.firstVisibleItemScrollOffset) {

        offset += listState.firstVisibleItemScrollOffset
    }
    val scrollDp = 300 - offset
    val animatedCardSize by animateDpAsState(
        targetValue = if (scrollDp <= 100) 100.dp else scrollDp.dp,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )
    val animatedElevation by animateDpAsState(
        targetValue = if (scrollDp < 110) 10.dp else 0.dp,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )
    val animatedTitleAlpha by animateFloatAsState(
        targetValue = if (scrollDp <= 110) 1f else 0f,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(key1 = animatedProgress) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value
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
                color = backgroundColor
                    .copy(alpha = animatedTitleAlpha),
                shape = shape
            )
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackButtonPressed,
                modifier = Modifier
                    .padding(Dimens.SmallPadding.size)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_icon),
                    tint = MaterialTheme.colors.onSurface
                )
            }
            Text(
                text = toolbarHeading,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = animatedTitleAlpha
                ),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(horizontal = Dimens.SmallPadding.size)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .alpha(1 - animatedTitleAlpha),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}

@Preview
@Composable
fun CollapsingToolbarPrev() {
    ThinkRchiveTheme {
        TopCollapsingToolbar(
            toolbarHeading = "Settings",
            listState = rememberLazyListState(),
            content = {
                Text(
                    text = "Settings",
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
        )
    }
}
