package org.example;

public class Watch implements Clock_Interface{
    protected int ID;
    protected int hour_hand=0;
    protected int minute_hand=0;
    protected String name;

    protected double price;
    public Watch(){}
    public Watch(String name, double price){
        this.name = name;
        this.price = price;
    }
    public Watch(int hour_hand, int minute_hand, String name, double price){
        this.hour_hand = hour_hand;
        this.minute_hand = minute_hand;
        this.name = name;
        this.price = price;
    }

    public Watch(int ID, int hour_hand, int minute_hand, String name, double price){
        this.ID = ID;
        this.hour_hand = hour_hand;
        this.minute_hand = minute_hand;
        this.name = name;
        this.price = price;
    }

    public int Get_ID() {
        return ID;
    }
    public void Set_ID(int ID) {
        this.ID = ID;
    }

    public double Get_Price(){
        return price;
    }
    public String Get_Name(){
        return name;
    }


    public String Get_Time() {
        String formattedHour = String.format("%02d", this.hour_hand);
        String formattedMinute = String.format("%02d", this.minute_hand);
        return formattedHour + ":" + formattedMinute;
    }

    public String toString() {
        return "Brand:" + name + ", Time:" + hour_hand + ":" + minute_hand + ", Price:" + price;
    }

    public void set(Enum_1 elem, int value) throws Exception {
        switch (elem) {
            case HOUR:
                if (value < 0 || value >= 24) {
                    throw new Exception("Invalid hour value. It should be between 0 and 23.");
                }
                this.hour_hand = value;
                break;
            case MINUTE:
                if (value < 0 || value >= 60) {
                    throw new Exception("Invalid minute value. It should be between 0 and 59.");
                }
                this.minute_hand = value;
                break;
            case SECOND:
                throw new Exception("Second hand is not supported.");
        }
    }
    @Override
    public void move_time(Enum_1 elem, int value) throws Exception {
        if (value < 0) {
            throw new Exception("Time value cannot be negative");
        }
        switch (elem) {
            case HOUR:
                this.hour_hand = (this.hour_hand + value) % 24;
                break;
            case MINUTE:
                this.minute_hand += value;
                if (this.minute_hand >= 60) {
                    this.hour_hand += this.minute_hand / 60;
                    this.minute_hand %= 60;
                }
                break;
            case SECOND:
                System.out.println("second error");
                break;
        }
    }
    public int getHour() {
        return this.hour_hand;
    }

    public int getMinute() {
        return this.minute_hand;
    }
}
