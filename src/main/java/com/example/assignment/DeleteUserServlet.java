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

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("ID"); // Getting ID as a string
        int ID = Integer.parseInt(idParam); // Converting to int (or long if needed)

        try {
            // Initialize database connection
            Connection con = Database.initializeDatabase();

            // Prepare SQL delete statement
            PreparedStatement ps = con.prepareStatement("DELETE FROM patient WHERE ID = ?");
            ps.setInt(1, ID); // Set the ID value in the prepared statement

            // Execute delete operation
            int rowsAffected = ps.executeUpdate();

            ps.close();
            con.close();

            // Redirect to success or error page based on the result
            if (rowsAffected > 0) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("error.jsp"); // Redirect to error page if no rows were affected
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to error page in case of exception
        }
    }
}
