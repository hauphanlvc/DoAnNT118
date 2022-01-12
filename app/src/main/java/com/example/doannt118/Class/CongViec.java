package com.example.doannt118.Class;

public class CongViec {
    String ten_cong_viec;
    boolean done;
    public CongViec(String ten_cong_viec)
    {
        this.ten_cong_viec = ten_cong_viec;
    }
    public CongViec(String ten_cong_viec,boolean done)
    {
        this.ten_cong_viec = ten_cong_viec;
        this.done = done;
    }

    public boolean getDone() {
        return this.done;
    }
    public String getTen_cong_viec()
    {
        return  this.ten_cong_viec;
    }
    public void setTen_cong_viec(String ten_cong_viec)
    {
        this.ten_cong_viec = ten_cong_viec;
    }
    public void setDone(boolean done)
    {
        this.done = done;
    }
}
