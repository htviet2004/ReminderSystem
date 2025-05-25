package controller;

import dao.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class TaskServlet extends HttpServlet {
    private TaskDAO taskDAO = new TaskDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy userId từ session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null || userId <= 0) {
            response.getWriter().println("Người dùng chưa đăng nhập!");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String deadline = request.getParameter("deadline");

        Task task = new Task(userId, title, description, deadline);

        TaskDAO taskDAO = new TaskDAO();
        if (taskDAO.insertTask(task)) {
            response.sendRedirect("task-list.jsp");
        } else {
            response.sendRedirect("task-list.jsp?error=1");
        }
    }
}