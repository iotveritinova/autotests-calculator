package ru.neoflex.dao;

import java.sql.*;

public class MySqlConnector {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testimony?useUnicode=true&serverTimezone=UTC", "user01", "user01");
            System.out.println("Успешное поключение к базе данных");
            //кириллица не выводилась без изменения кодировки autotests-calculator\.idea\encodings.xml
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения " + e);
        }
        return connection;
    }

    public static ResultSet selectAllFromBilling(String currentmonth) throws SQLException {
        Statement st = getConnection().createStatement();
        ResultSet resultSet = st.executeQuery(String.format("SELECT * FROM billing_period WHERE currentmonth = \"%s\";", currentmonth));
        return resultSet;
    }

    public static ResultSet selectAllFromPrices() throws SQLException {
        Statement st = getConnection().createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM price_guide");
        return resultSet;
    }
}
