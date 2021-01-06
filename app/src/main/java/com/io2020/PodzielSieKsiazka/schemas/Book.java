package com.io2020.PodzielSieKsiazka.schemas;

import androidx.annotation.Nullable;

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
    @SerializedName("description")
    private String description;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("isAvaible")
    private boolean isAvailable;
    @SerializedName("addedDate")
    private String addedDate;
    @Nullable
    @SerializedName("owner")
    private Owner owner;

    @Nullable
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(@Nullable Owner owner) {
        this.owner = owner;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public Book(String title, String author, String isbn, String description, String imgUrl) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.imgUrl = imgUrl;

    }


}
