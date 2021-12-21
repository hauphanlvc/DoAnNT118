package com.example.doannt118;


public class User {
    public String email;
    public String password;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public String getPassword()
    {
        return this.password;
    }
    public String getEmail()
    {
        return this.email;
    }
}
