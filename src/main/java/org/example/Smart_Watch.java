package org.example;

public class Smart_Watch extends Watch {

    private int seconds_hand = 0;
    public Smart_Watch(){
        super();
    }
    @Override
    public double Get_Price() {
        return super.Get_Price();
    }
    @Override
    public String Get_Name() {
        return super.Get_Name();
    }
    public Smart_Watch(int hour_hand, int minute_hand, int seconds_hand,String name, double price){
        super(hour_hand, minute_hand, name, price);
        this.seconds_hand = seconds_hand;
    }
    @Override
    public void set(Enum_1 elem, int value) throws Exception {

        if (value < 0 || (elem == Enum_1.SECOND && value >= 60)) {
            throw new Exception("Invalid time value");
        }
        if (elem == Enum_1.SECOND) {
            this.seconds_hand = value;
        }
        else{
            super.set(elem, value);
        }
    }
    @Override
    public void move_time(Enum_1 elem, int value) throws Exception {
        if (value < 0) {
            throw new Exception("Time value cannot be negative");
        }
        if (elem == Enum_1.SECOND) {
            if (this.seconds_hand >= 60) {
                super.move_time(Enum_1.MINUTE, this.seconds_hand / 60);
                this.seconds_hand %= 60;
            }
        }
        else{
            super.move_time(elem, value);
        }

    }
    @Override
    public String toString() {
        return "Brand:" + name + ", Time:" + hour_hand + ":" + minute_hand + ":" + seconds_hand + ", Price:" + price;
    }

    public int getHour() {
        return this.hour_hand;
    }

    public int getMinute() {
        return this.minute_hand;
    }

    public int getSecond() {
        return this.seconds_hand;
    }

}
