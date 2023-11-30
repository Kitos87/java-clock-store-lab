package org.example;

public class Builder {
    public static Clock_Interface create_Watch(String type_of_watch, int id, int hour, int minute, String name, double price) {
        if ("Watch".equals(type_of_watch)) {
            Watch UsualWatch = new Watch(id, hour, minute, name, price);
            return UsualWatch;
        } else { throw new IllegalArgumentException("Данный вид часов отсутствует");}
    }
    public static Clock_Interface create_Watch(String type_of_watch, int id, int hour, int minute, int second, String name, double price) {
        if ("Smart_Watch".equals(type_of_watch)) {
            Smart_Watch SmartWatch = new Smart_Watch(id,hour, minute, second, name, price);
            return SmartWatch;
        }
        else { throw new IllegalArgumentException("Данный вид часов отсутствует");}
    }
}
