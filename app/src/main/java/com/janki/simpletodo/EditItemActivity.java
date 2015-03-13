package com.janki.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    private  String value;
   private int position;
   private EditText editItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tem);
        editItem = (EditText) findViewById(R.id.etField);
       value = getIntent().getStringExtra("value");
        position = getIntent().getIntExtra("position",0);
        editItem.setText(value);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
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

    public void saveItem(View view) {
        editItem = (EditText) findViewById(R.id.etField);

        Intent data = new Intent();
        data.putExtra("value", editItem.getText().toString());
        data.putExtra("position", position);
        data.putExtra("code", 200);

        setResult(RESULT_OK, data);
        finish();
    }
}
