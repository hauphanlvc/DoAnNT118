package com.example.doannt118;


import com.example.doannt118.Class.ProjectList;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String email;
    public String password;
    public ArrayList<ProjectList> project_list;

    public User() {
    }

    public User(String email, String password,ArrayList<ProjectList> project_list) {
        this.password = password;
        this.email = email;
//        this.project_list = project_list;
        this.project_list  = project_list;

    }

    public void setProject_list(ArrayList<ProjectList> project_list) {
        this.project_list = project_list;
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
