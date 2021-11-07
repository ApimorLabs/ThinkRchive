package work.racka.thinkrchive.ui.widgets.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class ThinkpadDetailsAppWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Test Glance",
                modifier = GlanceModifier
                    .padding(16.dp),
                style = TextStyle(color = ColorProvider(MaterialTheme.colorScheme.onPrimaryContainer))
            )
        }
    }
}