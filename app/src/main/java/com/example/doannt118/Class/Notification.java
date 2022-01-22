package com.example.doannt118.Class;

public class Notification {
    public String email_moi;
    public String project;
    public boolean agree;
    public boolean confirm;
    public String link_email_moi_project;
    public Notification()
    {}
    public Notification(String email_moi,String project,boolean agree,boolean confirm,String link_email_moi_project)
    {
        this.email_moi = email_moi;
        this.project = project;
        this.agree = agree;
        this.confirm = confirm;
        this.link_email_moi_project = link_email_moi_project;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public void setEmail_moi(String email_moi) {
        this.email_moi = email_moi;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public boolean isAgree() {
        return agree;
    }

    public String getEmail_moi() {
        return email_moi;
    }

    public String getProject() {
        return project;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public String getLink_email_moi_project() {
        return link_email_moi_project;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public void setLink_email_moi_project(String link_email_moi_project) {
        this.link_email_moi_project = link_email_moi_project;
    }
}
