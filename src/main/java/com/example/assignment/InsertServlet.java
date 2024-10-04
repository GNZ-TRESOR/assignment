package com.example.assignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/insert")
public class InsertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("Name");
        String phone = request.getParameter("Phone");
        String emergency_phone = request.getParameter("Emergency_phone");
        String ID = request.getParameter("ID");
        String birth_date_str = request.getParameter("Birth_date");

        // Convert birth_date to java.sql.Date
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
            PreparedStatement ps = con.prepareStatement("INSERT INTO patient (Name, Phone, Emergency_phone, ID, Birth_date) VALUES (?, ?, ?, ?, ?)");

            // Set parameters
            ps.setString(1, name);
            ps.setString(2, phone); // Use String for phone numbers
            ps.setString(3, emergency_phone); // Use String for emergency phone numbers
            ps.setString(4, ID); // Use String for ID, or long if it's purely numeric
            ps.setDate(5, birth_date); // Use java.sql.Date for date

            // Execute the query
            ps.executeUpdate();

            // Close resources
            ps.close();
            con.close();

            // Redirect to success page
            response.sendRedirect("success.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


