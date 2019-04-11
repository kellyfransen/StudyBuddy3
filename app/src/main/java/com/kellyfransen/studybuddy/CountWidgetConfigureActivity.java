package com.kellyfransen.studybuddy;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The configuration screen for the {@link CountWidget CountWidget} AppWidget.
 */
public class CountWidgetConfigureActivity extends Activity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    public static ArrayList<String> activeButtons = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_widget_configure);


        ArrayAdapter widgetListViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activeButtons);
        ListView widgetListView = (ListView) findViewById(R.id.widgetListView);
        widgetListView.setAdapter(widgetListViewAdapter);
        widgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context context = CountWidgetConfigureActivity.this;
                Toast.makeText(CountWidgetConfigureActivity.this, activeButtons.get(position), Toast.LENGTH_SHORT).show();


                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                CountWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);

                finish();
            }
        });

    }

}


