package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Lấy thông tin user từ database
        User user = userDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            // Đăng nhập thành công -> tạo session
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Lưu đối tượng User vào session
            session.setAttribute("userId", user.getId()); // Lưu userId vào session
            response.sendRedirect("task-list.jsp"); // Chuyển tới trang danh sách task
        } else {
            // Đăng nhập thất bại -> thông báo lỗi
            request.setAttribute("errorMsg", "Sai username hoặc password!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        // Chuyển đến trang login khi gọi GET
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}