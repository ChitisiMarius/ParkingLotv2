/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.park.parkinglot.servlet.car;

import com.park.parkinglot.common.CarDetails;
import com.park.parkinglot.common.UserDetails;
import com.park.parkinglot.ejb.CarBean;
import com.park.parkinglot.ejb.UserBean;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marius
 */

@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"AdminRole"}))
@WebServlet(name = "EditCar", urlPatterns = {"/Cars/Update"})
public class EditCar extends HttpServlet {

    @Inject
    UserBean userBean;
    
    @Inject
    CarBean carBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        List<UserDetails> users = userBean.getAllUsers();
        request.setAttribute("users",users);
        
        int carId=Integer.parseInt(request.getParameter("id"));
        CarDetails car=carBean.findbById(carId);
        request.setAttribute("car", car);
        
        request.getRequestDispatcher("/WEB-INF/pages/car/editCar.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        int ownerId = Integer.parseInt(request.getParameter("owner_id"));
        int carId=Integer.parseInt(request.getParameter("car_id"));
        
        carBean.updateCar(carId, licensePlate,parkingSpot,ownerId);
        
        response.sendRedirect(request.getContextPath()+ "/Cars");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}