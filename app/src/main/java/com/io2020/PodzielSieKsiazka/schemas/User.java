package com.io2020.PodzielSieKsiazka.schemas;

import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Book> books;

    public User(int id, String name, List<Book> bookList){
        this.id = id;
        this.name = name;
        this.books = bookList;
    }

    public int get_id() {
        return this.id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public List<Book> getBookList() {
        return books;
    }

    public void setBookList(List<Book> bookList) {
        this.books = bookList;
    }
}
