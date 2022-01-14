package com.example.doannt118;


import java.util.ArrayList;

public class User {
    public String email;
    public String password;


    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
//        this.project_list = project_list;


    }


    public void setEmail(String email)
    {
        this.email = email;

    }
    public void setPassword(String password)
    {
        this.password = password;
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
