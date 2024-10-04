package com.example.assignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/update")
public class UpdateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("Name");
        String phone = request.getParameter("Phone"); // Using String for phone
        String emergency_phone = request.getParameter("Emergency_phone"); // String for emergency phone
        String ID = request.getParameter("ID"); // Assuming ID is String; use long if numeric
        String birth_date_str = request.getParameter("Birth_date"); // Getting date as a string

        // Convert the birth date string to java.sql.Date
        java.sql.Date birth_date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(birth_date_str);
            birth_date = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Connection con = Database.initializeDatabase();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE patient SET Name = ?, Phone = ?, Emergency_phone = ?, Birth_date = ? WHERE ID = ?"
            );
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, emergency_phone);
            ps.setDate(4, birth_date); // Using java.sql.Date for the birth date
            ps.setString(5, ID); // Assuming ID is a String, change to setLong if it's numeric

            ps.executeUpdate();

            ps.close();
            con.close();

            response.sendRedirect("success.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
