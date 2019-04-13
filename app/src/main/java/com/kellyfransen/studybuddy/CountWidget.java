package com.kellyfransen.studybuddy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.kellyfransen.studybuddy.models.CountButton;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CountWidgetConfigureActivity CountWidgetConfigureActivity}
 */
public class CountWidget extends AppWidgetProvider {
    private static final String action = "ACTION";
    //private static int position;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        String name = CountWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        int pos = CountWidgetConfigureActivity.loadPosition(context, appWidgetId);
        //position = CountWidgetConfigureActivity.LoadPosition(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
        int icon = context.getResources().getIdentifier(name + "_icon", "drawable", context.getPackageName());

        views.setInt(R.id.widgetButton, "setBackgroundResource", icon);
        Intent intent = new Intent(context, CountWidget.class);
        intent.putExtra("position", pos);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (action.equals(intent.getAction()) && Health.healthButtons.size() > 0) {
            int position = intent.getIntExtra("position", 0);
           // Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();
            Health.healthButtons.get(position).increment();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
            views.setTextViewText(R.id.widgetButton, Health.healthButtons.get(position).count + "");

            // This time we don't have widgetId. Reaching our widget with that way

            ComponentName appWidget = new ComponentName(context, CountWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // Update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }
}
