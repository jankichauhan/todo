package com.janki.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.janki.simpletodo.R;
import com.janki.simpletodo.models.Item;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;


public class TodoActivity extends ActionBarActivity {

    private final int REQUEST_CODE = 20;
    private List<Item> todoItems;
    private ArrayAdapter<Item> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();
        etNewItem = (EditText) findViewById(R.id.etNewItems);
        lvItems = (ListView) findViewById(R.id.lvitems);
        todoAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setListViewListener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }


    private void setListViewListener() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int position, long id) {

                todoItems.get(position).delete();
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("value", todoItems.get(position).getName());

                startActivityForResult(i, REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String value = data.getExtras().getString("value");
            int position = data.getExtras().getInt("position", 0);

            Item editItem = todoItems.get(position);
            editItem.setName(value);
            editItem.save();
            todoAdapter.notifyDataSetChanged();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddedItem(View v) {
        String itemText = etNewItem.getText().toString();
        Item newItem = new Item(itemText);
        todoAdapter.add(newItem);
        newItem.save();
        etNewItem.setText("");
    }

    private void readItems() {
        todoItems = Item.getAll();

    }

}
