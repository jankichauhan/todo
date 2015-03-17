package com.janki.simpletodo.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.janki.simpletodo.R;
import com.janki.simpletodo.adapters.ItemAdapter;
import com.janki.simpletodo.fragments.AddItemDialog;
import com.janki.simpletodo.models.Item;

import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;
import static com.janki.simpletodo.fragments.AddItemDialog.AddItemDialogListener;
import static com.janki.simpletodo.fragments.AddItemDialog.EditItemDialogListener;

public class TodoActivity extends FragmentActivity implements AddItemDialogListener, EditItemDialogListener {

    private final int REQUEST_CODE = 20;
    private List<Item> todoItems;
    private ItemAdapter todoAdapter;
    private ListView lvItems;
    //  private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();
        // etNewItem = (EditText) findViewById(R.id.etNewItems);
        lvItems = (ListView) findViewById(R.id.lvitems);
        todoAdapter = new ItemAdapter(this, todoItems);
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

//                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
//                i.putExtra("position", position);
//                i.putExtra("value", todoItems.get(position).getName());
//
//                startActivityForResult(i, REQUEST_CODE);

                DialogFragment addDialog = AddItemDialog.newInstance(todoItems.get(position));
                addDialog.show(getFragmentManager(), "add_item_dialog");
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

    public void addNewItem(View view) {
        DialogFragment addDialog = new AddItemDialog();
        addDialog.show(getFragmentManager(), "add_item_dialog");
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

    @Override
    public void onAddItem(Item item) {
        item.setPosition(todoItems.size());
        todoAdapter.add(item);
        item.save();
    }

    @Override
    public void onEditItem(Item item, Long id) {
        todoItems.set(item.getPosition(), item);
        todoAdapter.notifyDataSetChanged();
        Item editItem = Item.load(Item.class, id);
        editItem.setName(item.getName());
        editItem.setDate(item.getDate());
        editItem.save();
    }

    private void readItems() {
        todoItems = Item.getAll();

    }


}
