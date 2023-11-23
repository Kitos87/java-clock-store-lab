package org.example;
import java.util.*;
public class Clock_store implements Iterable<Clock_Interface>{
    private List<Clock_Interface> clocks = new ArrayList<>();
    private List<IOserver> server = new ArrayList<>();

    private void events() {
        server.forEach(o -> o.event(this));
    }
    public void sub(IOserver obs) { server.add(obs); }
    public void un_sub(IOserver obs) {
        server.remove(obs);
    }
    public void removeClock(Clock_Interface clock) {
        clocks.remove(clock);
        events();
    }

    public List<Clock_Interface> getClocks() {
        return new ArrayList<>(clocks);
    }


    @Override
    public Iterator<Clock_Interface> iterator() {
        return clocks.iterator();
    }
    public void addClock(Clock_Interface clock) {
        clocks.add(clock);
        events();
    }


    public void moveTime(Enum_1 elem, int value) throws Exception {
        if (value < 0) {
            throw new Exception("Time value cannot be negative");
        }
        for (Clock_Interface clock : clocks) {
            clock.move_time(elem, value);
        }
    }
    public Clock_Interface getMostExpensiveClock() {
        if (clocks.isEmpty()) {
            return null;
        }
        Clock_Interface expensive_clock = clocks.get(0);
        for (Clock_Interface clock : clocks) {
            if (clock.Get_Price() > expensive_clock.Get_Price()) {
                expensive_clock = clock;
            }
        }
        return expensive_clock;
    }
    public String getMostCommonBrand() {
        if (clocks.isEmpty()) {
            return null;
        }
        Map<String, Integer> Count_brand = new HashMap<>();
        for (Clock_Interface clock : clocks) {
            String brand = clock.Get_Name();
            int count = Count_brand.getOrDefault(brand, 0) + 1;
            Count_brand.put(brand, count);
        }
        String most_common_brand = null;
        int most_common_count = 0;
        for (Map.Entry<String, Integer> brandEntry : Count_brand.entrySet()) {
            if (brandEntry.getValue() > most_common_count) {
                most_common_count = brandEntry.getValue();
                most_common_brand = brandEntry.getKey();
            }
        }
        return most_common_brand;
    }

    public List<String> getUniqueSortedBrands() {
        Set<String> unique_brands = new HashSet<>();
        for (Clock_Interface clock : clocks) {
            unique_brands.add(clock.Get_Name());
        }
        List<String> sorted_brands = new ArrayList<>(unique_brands);
        Collections.sort(sorted_brands);
        return sorted_brands;
    }
    public void setTimeForAllClocks(Enum_1 time, int value) {
        boolean isTimeSet = false;
        for (Clock_Interface clock : clocks) {
            try {
                clock.set(time, value);
                isTimeSet = true;
            } catch (Exception e) {
                System.out.println("Часы " + clock.Get_Name() + " не поддерживают установку " + time.name().toLowerCase());
            }
        }
        if (isTimeSet) {
            events();
        }
    }

}
