package com.angularstack.dailycash;


public class ReadData {

    public String name;
    public String email;
    public String phone;
    public String refferal;
    public int reward;

    public ReadData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public  ReadData(String name,String email,String phone,String refferal,int reward)
    {
        this.name = name;
        this.email=email;
        this.phone=phone;
        this.refferal=refferal;
        this.reward=reward;
    }

}
