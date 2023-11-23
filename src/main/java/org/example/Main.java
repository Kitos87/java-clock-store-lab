package org.example;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Clock_Interface casioWatch = Builder.create_Watch("Watch", 10, 20, "Casio", 50.0);
        Clock_Interface casio2Watch = Builder.create_Watch("Watch", 15, 20, "Casio", 150.0);
        Clock_Interface rolexWatch = Builder.create_Watch("Watch", 11, 45, "Rolex", 5000.0);
        Clock_Interface swatchWatch = Builder.create_Watch("Smart_Watch", 12, 30, 15, "Redmi Watch", 100.0);
        Clock_Interface appleSmartWatch = Builder.create_Watch("Smart_Watch", 10, 20, 30, "Apple Watch", 400.0);
        Clock_Interface samsungSmartWatch = Builder.create_Watch("Smart_Watch", 9, 15, 45, "Samsung Galaxy", 300.0);

        Clock_store myClockStore = new Clock_store();
        myClockStore.addClock(casioWatch);
        myClockStore.addClock(casio2Watch);
        myClockStore.addClock(rolexWatch);
        myClockStore.addClock(swatchWatch);
        myClockStore.addClock(appleSmartWatch);
        myClockStore.addClock(samsungSmartWatch);

        System.out.println("Состояние часов до изменения времени:");
        for (Clock_Interface clock : myClockStore) {
            System.out.println(clock.toString());
        }

        System.out.println("\nПеремещаем время на 15 минут вперед...");
        try {
            myClockStore.moveTime(Enum_1.MINUTE, 15);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при перемещении времени: " + e.getMessage());
        }

        System.out.println("\nСостояние часов после изменения времени:");
        for (Clock_Interface clock : myClockStore) {
            System.out.println(clock.toString());
        }

        Clock_Interface mostExpensiveClock = myClockStore.getMostExpensiveClock();
        if (mostExpensiveClock != null) {
            System.out.println("\nСамые дорогие часы стоят: " + mostExpensiveClock.Get_Price());
        }

        String mostCommonBrand = myClockStore.getMostCommonBrand();
        System.out.println("Самая распространенная марка: " + mostCommonBrand);

        List<String> uniqueSortedBrands = myClockStore.getUniqueSortedBrands();
        System.out.println("Уникальные отсортированные бренды: " + uniqueSortedBrands);

        System.out.println("\nУстанавливаем для всех часов новое время: 12:30:45");
        myClockStore.setTimeForAllClocks(Enum_1.HOUR, 12);
        myClockStore.setTimeForAllClocks(Enum_1.MINUTE, 30);
        myClockStore.setTimeForAllClocks(Enum_1.SECOND, 45);

        System.out.println("\nСостояние часов после установки времени:");
        for (Clock_Interface clock : myClockStore) {
            System.out.println(clock.toString());
        }
    }
}


