package org.example;
import com.example.visualjavafxapp.BModel;

import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.Types.NULL;


public class Clock_store implements Iterable<Clock_Interface>{
    private List<Clock_Interface> clocks = new ArrayList<>();
    private List<IOserver> server = new ArrayList<>();
    transient Connection c;


    void connect()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(
                    "jdbc:sqlite:C:/Users/kudko/CLOCKK/java-clock-store-lab/clockss.db");
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Can't open DB");
        } catch (SQLException ex) {
            System.out.println("Can't open DB");
        }
    }

    void selectAll(){

        clocks.clear();

        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("select * from clock_table");
            while (rs.next()) {
                if (rs.getInt("second") == NULL){
                clocks.add(new Watch(
                        rs.getInt("ID"),
                        rs.getInt("hour"),
                        rs.getInt("minute"),
                        rs.getString("name"),
                        rs.getDouble("price")
                        )
                    );
                }
                else {
                    clocks.add(new Smart_Watch(
                            rs.getInt("ID"),
                            rs.getInt("hour"),
                            rs.getInt("minute"),
                            rs.getInt("second"),
                            rs.getString("name"),
                            rs.getDouble("price")
                            )
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        events();
    }


    public Clock_store(){
        connect();
        selectAll();
    }

    private void events() {
        server.forEach(o -> o.event(this));
    }
    public void sub(IOserver obs) { server.add(obs); }
    public void un_sub(IOserver obs) {
        server.remove(obs);
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
        try {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO clock_table (hour, minute, second, name, price) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            int[] timeArray = parseTime(clock);

            statement.setInt(1, timeArray[0]);
            statement.setInt(2, timeArray[1]);
            if (timeArray.length == 3) {
                statement.setInt(3, timeArray[2]);
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setString(4, clock.Get_Name());
            statement.setDouble(5, clock.Get_Price());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating clock failed, no rows affected.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                clock.Set_ID((int)generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clocks.add(clock);
        events();
    }


    public void removeClock(Clock_Interface clock) {
        try {
            PreparedStatement statement = c.prepareStatement(
                    "DELETE FROM clock_table WHERE ID = ?");
            statement.setInt(1, clock.Get_ID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clocks.remove(clock);
        events();
    }

    public int[] parseTime(Clock_Interface clock) {
        String time = clock.Get_Time();
        String[] parts = time.split(":");
        int[] timeArray = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            timeArray[i] = Integer.parseInt(parts[i]);
        }
        return timeArray;
    }

    public void updateClock(Clock_Interface clock) {
        String sql = "UPDATE clock_table SET hour = ?, minute = ?, second = ? WHERE ID = ?";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            int[] timeArray = parseTime(clock);
            statement.setInt(1, timeArray[0]);
            statement.setInt(2, timeArray[1]);

            if (clock instanceof Smart_Watch && timeArray.length == 3) {
                statement.setInt(3, timeArray[2]);
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(4, clock.Get_ID());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Обновление часов не удалось, не затронуто ни одной строки.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
