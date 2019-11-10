package com.ualr.androidfinalproject.model;

public class Item {

    private int imageResId;
    private String name;
    private double price;
    private boolean isBrowseView;

    public Item(int imageResId, String name, double price, boolean isBrowseView) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.isBrowseView = isBrowseView;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBrowseView() {
        return isBrowseView;
    }

    public void setBrowseView(boolean browseView) {
        isBrowseView = browseView;
    }
}
