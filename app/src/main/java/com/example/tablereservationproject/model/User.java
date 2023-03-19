package com.example.tablereservationproject.model;

public class User {
    public static final String TABLE_NAME = "my_users";
    public static final String FIELD_USER_ID = "user_id";
    public static final String FIELD_USER_EMAIL = "user_email";
    public static final String FIELD_USER_NAME = "user_name";
    public static final String FIELD_USER_PASSWORD = "user_password";

    private String email;
    private String name;
    private String password;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
