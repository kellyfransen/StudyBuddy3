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
 * The configuration screen for the {@link CountWidget CountWidget} AppWidget.
 */
public class CountWidgetConfigureActivity extends Activity {
    public static ArrayList<String> activeButtons = new ArrayList<>();
    //public static ArrayList<CountWidget> countWidgets = new ArrayList<>();
    ListView widgetListView;

    private static final String PREFS_NAME = "com.kellyfransen.studybuddy.CountWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.count_widget_configure);
        widgetListView = findViewById(R.id.widgetListView);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activeButtons);
        widgetListView.setAdapter(adapter);
        widgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context context = CountWidgetConfigureActivity.this;
                String name = String.valueOf(parent.getItemAtPosition(position));
                saveTitlePref(context, mAppWidgetId, name);
                savePosition(context, mAppWidgetId, position);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.count_widget);
                views.setTextViewText(R.id.widgetButton, Health.healthButtons.get(0).count + "");


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


    {
//        mOnClickListener = new View.OnClickListener() {
//            public void onClick(View v) {
//                final Context context = CountWidgetConfigureActivity.this;
//
//                // When the button is clicked, store the string locally
//                String widgetText = mAppWidgetText.getText().toString();
//                saveTitlePref(context, mAppWidgetId, widgetText);
//
//                // It is the responsibility of the configuration activity to update the app widget
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                CountWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
//
//                // Make sure we pass back the original appWidgetId
//                Intent resultValue = new Intent();
//                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//                setResult(RESULT_OK, resultValue);
//                finish();
//            }
//        };
    }

    public CountWidgetConfigureActivity() {
        super();
    }

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

    public void savePosition(Context context, int appWidgetId, int position) {
        SharedPreferences.Editor prefs = context.getSharedPreferences("position", 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, position);
        prefs.apply();
    }

    static int loadPosition(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences("position", 0);
        int position = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0);
        return position;
    }
}
