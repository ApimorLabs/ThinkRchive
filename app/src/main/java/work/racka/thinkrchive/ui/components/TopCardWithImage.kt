package work.racka.thinkrchive.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onBackButtonPressed: () -> Unit = { }
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
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
                imageVector = Icons.Outlined.ArrowBack,
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
                    .size(290.dp)
                    .padding(Dimens.MediumPadding.size)
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
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png"
        )
    }
}
