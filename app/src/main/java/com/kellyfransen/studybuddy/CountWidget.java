package com.kellyfransen.studybuddy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);

        Intent intent = new Intent(context, CountWidget.class);
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

        if (action.equals(intent.getAction()) && Health.healthButtons.size()>0) {

            Health.healthButtons.get(0).increment();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
            //views.setTextViewText(R.id.widgetTextView, Health.healthButtons.get(0).count +"");
            // This time we don't have widgetId. Reaching our widget with that way
            Toast.makeText(context,Health.healthButtons.get(0).count+"" , Toast.LENGTH_SHORT).show();
            ComponentName appWidget = new ComponentName(context, CountWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // Update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }
}
