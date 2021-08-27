package work.racka.thinkrchive.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalAnimationApi
@Composable
fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    scope: CoroutineScope
) {
    val showScrollButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    AnimatedVisibility(
        visible = showScrollButton,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        Box(
            modifier = modifier
                .padding(vertical = Dimens.MediumPadding.size)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                shape = CircleShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = Dimens.MediumPadding.size)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowUpward,
                        contentDescription = "Scroll Up button"
                    )
                    Spacer(modifier = Modifier.width(Dimens.SmallPadding.size))
                    Text(
                        text = "Scroll Up",
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ScrollButtonPreview() {
    ThinkRchiveTheme {
        ScrollToTopButton(
            listState = rememberLazyListState(),
            scope = rememberCoroutineScope()
        )
    }
}
