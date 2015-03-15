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

    public Item() {
        super();
    }

    public Item(String name) {
        super();
        this.name = name;
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

    public static List<Item> getAll() {
        return new Select().from(Item.class).execute();
    }
}
