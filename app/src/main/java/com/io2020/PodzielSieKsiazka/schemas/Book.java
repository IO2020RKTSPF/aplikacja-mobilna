package com.io2020.PodzielSieKsiazka.schemas;

import com.google.gson.annotations.SerializedName;

public class Book {
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
    @SerializedName("userId")
    private int userId;


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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Book(String title, String author, String isbn, String description, String imgUrl, int userId) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.imgUrl = imgUrl;
        this.userId = userId;
    }


}
