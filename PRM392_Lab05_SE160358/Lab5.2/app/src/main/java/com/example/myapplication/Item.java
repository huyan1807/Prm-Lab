package com.example.myapplication;



public class Item {
    private int imageRes;
    private String title;
    private String description;
    private String category;

    public Item(int imageRes, String title, String description, String category) {
        this.imageRes = imageRes;
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public int getImageRes() { return imageRes; }
    public void setImageRes(int imageRes) { this.imageRes = imageRes; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
