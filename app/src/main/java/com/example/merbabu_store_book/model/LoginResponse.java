package com.example.merbabu_store_book.model;

public class LoginResponse {
    public boolean success;
    public String message;
    public User user;

    public class User {
        public int id;
        public String name;
        public String email;
    }
}
