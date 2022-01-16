package com.example.doannt118.Class;

public class TenThanhVienThe {
    public String ten_thanh_vien;
    public boolean is_added;
    public TenThanhVienThe()
    {

    }
    public TenThanhVienThe(String ten_thanh_vien, boolean is_added)
    {
        this.ten_thanh_vien = ten_thanh_vien;
        this.is_added = is_added;
    }
    public String getTen_thanh_vien()
    {
        return this.ten_thanh_vien;

    }

    public void setTen_thanh_vien(String ten_thanh_vien) {
        this.ten_thanh_vien = ten_thanh_vien;
    }

    public boolean isIs_added() {
        return is_added;
    }

    public void setIs_added(boolean is_added) {
        this.is_added = is_added;
    }

}
