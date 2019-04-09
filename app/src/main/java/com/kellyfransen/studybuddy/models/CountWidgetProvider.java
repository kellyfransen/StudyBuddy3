package com.kellyfransen.studybuddy.models;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.kellyfransen.studybuddy.MainActivity;
import com.kellyfransen.studybuddy.R;

public class CountWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countwidget);
            views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }
}
