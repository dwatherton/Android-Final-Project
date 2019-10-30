package com.ualr.androidfinalproject.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

public class ItemViewModel extends ViewModel {

    private MutableLiveData<SortedList<Item>> items;

    public ItemViewModel() {
        this.items = new MutableLiveData<>();
    }

    public LiveData<SortedList<Item>> getItems() {
        return this.items;
    }

    public void setItems(SortedList<Item> itemList) {
        this.items.setValue(itemList);
    }

    public Item getItemAt(int position) {
        return this.items.getValue().get(position);
    }

    public void setItemAt(int position, Item item) {
        SortedList<Item> items = this.items.getValue();
        items.updateItemAt(position, item);
        setItems(items);
    }

    public void clearCart() {
        items.getValue().replaceAll();
        setItems(null);
    }
}
