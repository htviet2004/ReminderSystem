package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = userDAO.getNextId();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(id, username, password, email);

        if (userDAO.insertUser(user)) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        } else {
            request.setAttribute("errorMsg", "Đăng ký thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Chuyển đến trang đăng ký khi gọi GET
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}