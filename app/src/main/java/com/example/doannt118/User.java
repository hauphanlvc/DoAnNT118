package com.example.doannt118;


import java.util.ArrayList;
import java.util.List;

public class User {
    public String email;
    public String password;
    public ArrayList<String> project_list;
    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
        this.project_list = new ArrayList<String>(0);

    }

    public void setProject_list(ArrayList<String> project_list) {
        this.project_list = project_list;
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
