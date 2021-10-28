package work.racka.thinkrchive.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@Composable
fun ClickableEntry(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    description: String,
    onEntryClick: () -> Unit = { }
) {

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onEntryClick() }
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.UpperMediumPadding.size,
                vertical = Dimens.MediumPadding.size
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = Dimens.SmallPadding.size)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = currentSettingIcon,
//                    contentDescription = null,
//                    tint = MaterialTheme.colors.onSurface
//                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
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
fun ClickableEntryPrev() {
    ThinkRchiveTheme {
        ClickableEntry(title = "This Title", subtitle = "2030 TZS", description = "Descr.....")
    }
}