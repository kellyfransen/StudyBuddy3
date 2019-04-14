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
 * This is the count widget, it includes all the code for making the buttons in the widget work
 * I modified the standard code that is created when you make a new widget in the project
 */
public class CountWidget extends AppWidgetProvider {
    private static final String action = "ACTION";
    private static int widgetId;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context, appWidgetManager, widgetId);
    }


    //what to do on update of the widget
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        //get name and position in the activeButtons list array
        String name = CountWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        int pos = CountWidgetConfigureActivity.loadPosition(context, appWidgetId);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);

        //search for the right image for the widget
        //and set it as background by setting the setBackgroundResource integer in the button
        int icon = context.getResources().getIdentifier(name + "_icon", "drawable", context.getPackageName());
        views.setInt(R.id.widgetButton, "setBackgroundResource", icon);

        //create intent with action and add the position int
        Intent clickIntent = new Intent(context, CountWidget.class);
        clickIntent.putExtra("position", pos);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickIntent.setAction(action);

        //broadcast PendingIntent to send on click
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);

        //tell the widgetmanager to update
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    //receives the broadcast on click
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //check if the action is correct and if there are any buttons in the healthButtons array
        if (action.equals(intent.getAction()) && Health.healthButtons.size() > 0) {

            //get position from intent
            int position = intent.getIntExtra("position", 0);
            widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);

            //increment the countbutton count value
            Health.healthButtons.get(position).increment();

            //create the remoteView needed for the widget and update the number
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
            views.setTextViewText(R.id.widgetButton, Health.healthButtons.get(position).count + "");


            //create widgetmanager and update widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            //IMPORTANT: pass widgetId here to address only the widget you want to update
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }
}
