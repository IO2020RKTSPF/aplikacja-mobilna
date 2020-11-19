package com.io2020.PodzielSieKsiazka.schemas;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("isAvaible")
    private boolean isAvailable;
    @SerializedName("description")
    private String description;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("addedDate")
    private String addedDate;
    @SerializedName("userId")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Book(int id, String title, String author, String isbn, boolean isAvailable, String description, String imgUrl, String addedDate, int userId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.description = description;
        this.imgUrl = imgUrl;
        this.addedDate = addedDate;
        this.userId = userId;
    }


}
