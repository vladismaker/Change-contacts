package com.example.chengecontacts;

import android.graphics.Bitmap;

public class Contact
{
    private String id;
    private String name  = "";
    private String phone = "";
    private int image;
    private Bitmap imageBitmap;
    private int imageSelect;
    private boolean check;

    public Contact() {
    }

    public Contact(String name, String phone, int image, int imageSelect) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.imageSelect = imageSelect;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getImageSelect() {
        return imageSelect;
    }
    public void setImageSelect(int imageSelect) {
        this.imageSelect = imageSelect;
    }

    public boolean isCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
}