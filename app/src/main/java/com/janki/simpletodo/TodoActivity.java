package com.janki.simpletodo;

import android.content.Intent;
import android.opengl.EGLExt;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.widget.AdapterView.*;


public class TodoActivity extends ActionBarActivity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private final int REQUEST_CODE = 20;

    private EditText etNewItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItem();
        etNewItem = (EditText) findViewById(R.id.etNewItems);
        lvItems = (ListView) findViewById(R.id.lvitems);
//       populateArrayItems();
        todoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, todoItems);
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

                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                saveItem();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("position", position);
                i.putExtra("value", todoItems.get(position));

                startActivityForResult(i, REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            String value = data.getExtras().getString("value");
            int position = data.getExtras().getInt("position",0);

            todoItems.remove(position);
            todoItems.add(position,value);
            todoAdapter.notifyDataSetChanged();
            saveItem();
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

    private void populateArrayItems() {

        todoItems = new ArrayList<String>();
        todoItems.add("Item 1");
        todoItems.add("Item 2");
        todoItems.add("Item 3");
    }

    public void onAddedItem(View v)
    {
        String itemText = etNewItem.getText().toString();
        todoAdapter.add(itemText);
        etNewItem.setText("");
        saveItem();
    }

    private void readItem()
    {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch (IOException e)
        {
            todoItems = new ArrayList<String>();
        }
    }

    private void saveItem()
    {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, todoItems);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
