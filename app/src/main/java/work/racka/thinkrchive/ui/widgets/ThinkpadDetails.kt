package work.racka.thinkrchive.ui.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.widgets.components.ThinkpadDetailsAppWidget

/**
 * Implementation of App Widget functionality.
 */
class ThinkpadDetails : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = ThinkpadDetailsAppWidget()

//    override fun onUpdate(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetIds: IntArray
//    ) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        // There may be multiple widgets active, so update all of them
//        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }
//    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.thinkpad_details)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}