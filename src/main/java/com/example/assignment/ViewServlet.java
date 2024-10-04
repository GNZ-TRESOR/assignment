package com.example.assignment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/viewPatients")
public class ViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Establish database connection
            Connection con = Database.initializeDatabase();

            // SQL query to get all patients
            PreparedStatement ps = con.prepareStatement("SELECT * FROM patient");
            ResultSet rs = ps.executeQuery();

            // Set the ResultSet as an attribute for the JSP page to use
            request.setAttribute("patients", rs);

            // Forward request to the JSP page for display
            request.getRequestDispatcher("viewPatients.jsp").forward(request, response);

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
