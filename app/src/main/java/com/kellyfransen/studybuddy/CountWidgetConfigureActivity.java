package com.kellyfransen.studybuddy;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

import static com.kellyfransen.studybuddy.R.id.widgetListView;

/**
 * This is the code for the initial popup screen when the widget is created
 * so the user can choose which of the buttons (first created in health screen) to add as widget
 * To make it a lot quicker to increment a most used button
 */
public class CountWidgetConfigureActivity extends Activity {
    //this ArrayList stores the created buttons
    public static ArrayList<String> activeButtons = new ArrayList<>();
    ListView widgetListView;

    //create the references for saving the position and name so they can be exchanged between widget config and the widget itself
    private static final String PREFS_NAME = "com.kellyfransen.studybuddy.CountWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.count_widget_configure);
        //this is the listview for selecting which button the user wants to create as widget
        widgetListView = findViewById(R.id.widgetListView);
        //which needs an adapter of course
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activeButtons);
        widgetListView.setAdapter(adapter);

        //check which item is selected from the list
        widgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context context = CountWidgetConfigureActivity.this;

                //get the name of the button from the ArrayList of buttons
                String name = String.valueOf(parent.getItemAtPosition(position));

                //save name and position in the sharedPreferences xml file
                saveTitlePref(context, mAppWidgetId, name);
                savePosition(context, mAppWidgetId, position);

                //create remoteview and set the text on the button to the count value
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
                views.setTextViewText(R.id.widgetButton, Health.healthButtons.get(position).count + "");


                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                CountWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent intent = new Intent();
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


    }


    public CountWidgetConfigureActivity() {
        super();
    }
//this function comes with the widget when it is made in the project
    // It normally stores a string which has to be added to a button in the example widget
    //I used it to pass the name of the button

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    //Function based on the saveTitlePref: does the same but for the position int
    //save position integer to sharedpreferences
    public void savePosition(Context context, int appWidgetId, int position) {
        SharedPreferences.Editor prefs = context.getSharedPreferences("position", 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, position);
        prefs.apply();
    }

    //retrieve position from the sharedPreferences
    static int loadPosition(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences("position", 0);
        int position = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0);
        return position;
    }
}
