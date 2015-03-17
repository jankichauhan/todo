package com.janki.simpletodo.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.janki.simpletodo.R;
import com.janki.simpletodo.models.Item;
import java.util.Calendar;

/**
 * Created by janki on 3/16/15.
 */
public class AddItemDialog extends DialogFragment {

    private EditText etAddItem;
    private DatePicker dpDueDate;
    private Long itemId;

    public AddItemDialog() {

    }

    public static AddItemDialog newInstance(Item item) {

        AddItemDialog frag = new AddItemDialog();
        Bundle args = new Bundle();
        args.putLong("id", item.getId());
        args.putInt("position", item.getPosition());
        args.putString("name", item.getName());
        args.putString("date", item.getDate());
        frag.setArguments(args);
        return frag;
    }
    public interface AddItemDialogListener {
        public void onAddItem(Item item);
    }
    public interface EditItemDialogListener {
        public void onEditItem(Item item, Long id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        if(getArguments() != null){
            builder.setTitle(R.string.edit_label);
        }
        else{
            builder.setTitle(R.string.add_label);
        }
        View view = inflater.inflate(R.layout.add_item, null, false);
        etAddItem = (EditText) view.findViewById(R.id.etAddItemText);
        if(getArguments()!= null){
            etAddItem.setText(getArguments().getString("name"));
            itemId = getArguments().getLong("id");
        }
        etAddItem.requestFocus();
        dpDueDate = (DatePicker) view.findViewById(R.id.dpDueDate);
        if(getArguments() != null){
            String [] date = getArguments().getString("date").split("/");
            dpDueDate.updateDate(Integer.parseInt(date[2]),Integer.parseInt(date[0]),Integer.parseInt(date[1]));
        }

        builder.setView(view)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialogInterface, int i) {
                                EditItemDialogListener editListener = (EditItemDialogListener) getActivity();
                                AddItemDialogListener addListener = (AddItemDialogListener) getActivity();
                                String date = dpDueDate.getMonth() + "/"
                                        + dpDueDate.getDayOfMonth() + "/"
                                        + dpDueDate.getYear();
                                if (getArguments() != null) {
                                    editListener.onEditItem(new Item(etAddItem.getText().toString(), date, 0),itemId);

                                } else {
                                    addListener.onAddItem(new Item(etAddItem.getText().toString(), date, 0));
                                }
                            }
                        })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

        return builder.create();
    }

//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        // Do something with the date chosen by the user
//    }
}
