package com.cyt.jvm.ClassLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test8 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cyt_mybatis", "root", "1234");
    }
}
