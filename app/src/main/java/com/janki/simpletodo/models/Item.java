package com.janki.simpletodo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by janki on 3/15/15.
 */
@Table(name = "Items")
public class Item extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "date")
    public String date;

    public int position;

    public Item() {
        super();
    }

    public Item(String name, String date, int position) {
        super();
        this.name = name;
        this.date = date;
        this.position = position;
    }

    public static List<Item> getAll() {
        return new Select().from(Item.class).execute();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
