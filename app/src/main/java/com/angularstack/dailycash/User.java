package com.angularstack.dailycash;

public class User {

    public String name;
    public String email;
    public String phone;
    public String refferal;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name,String email,String phone,String refferal)
    {
        this.name = name;
        this.email=email;
        this.phone=phone;
        this.refferal=refferal;
    }

}
