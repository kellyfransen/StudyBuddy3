package com.kellyfransen.studybuddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.kellyfransen.studybuddy.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Planning extends AppCompatActivity {

    private static final String TAG = "Planning";
    ArrayList<Intent> intentsList = new ArrayList<>();
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create swipeable listview menu
        setContentView(R.layout.activity_planning);
        final SwipeMenuListView listView = findViewById(R.id.listView);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create more item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                openItem.setWidth(200);
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.GRAY);
                menu.addMenuItem(openItem);
                openItem.setTitle("More");

                // create delete item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                deleteItem.setWidth(200);
                deleteItem.setIcon(R.drawable.cross);
                menu.addMenuItem(deleteItem);

            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //open new page with course listview
                        startActivity(intentsList.get(position));
                        break;
                    case 1:
                        // delete course
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        break;
                }
                return false;
            }
        });
    }

    //add item to into the add field
    public void onAddItem(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();

        //create intent that creates a new activity for a list item and copies the text of the list item
        Intent intent = new Intent(Planning.this, Course.class);
        intent.putExtra("name", itemText);
        intentsList.add(intent);
    }

    //read the item that the user types
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "courses.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    //put the item that the user typed in the listview
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "courses.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
