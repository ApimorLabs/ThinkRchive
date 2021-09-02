package work.racka.thinkrchive.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SecurityUpdate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@Composable
fun AboutTopSection(
    modifier: Modifier = Modifier,
    appName: String,
    version: String,
    appLogo: Painter,
    onCheckUpdatesClicked: () -> Unit = { }
) {
    Column(
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                300,
                easing = LinearOutSlowInEasing
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp),
            painter = appLogo,
            contentDescription = stringResource(id = R.string.app_logo)
        )

        Text(
            text = appName,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(4.dp)
        )

        Text(
            text = "Version: $version",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(4.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(4.dp),
            onClick = onCheckUpdatesClicked,
            shape = CircleShape,
            elevation = ButtonDefaults
                .elevation(0.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.SecurityUpdate,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Check Updates",
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

@Preview
@Composable
private fun AboutTopSectionPrev() {
    ThinkRchiveTheme {
        AboutTopSection(
            appName = "ThinkRchive",
            version = "1.0.1-alpha01",
            appLogo = painterResource(id = R.drawable.app_icon)
        )
    }
}
